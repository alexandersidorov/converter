package task.smartsoft.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import task.smartsoft.domain.Currency;
import task.smartsoft.domain.Rate;
import task.smartsoft.utils.Today;
import task.smartsoft.utils.TransferDateToString;

import javax.annotation.PostConstruct;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;

@Service
public class ExchRatesLoader {

    @Value("${basePathXML}")
    private String path;

    @Autowired
    CurrencyService currencyService;

    @Autowired
    RateService rateService;

    public ExchRatesLoader() {}

    @PostConstruct
    public void loadActualExchangeRates(){
        load(Today.get());
    }

    private void load(Date date){

        //преобразуем дату в строковый вид формата dd/MM/yyyy
        String dataStr = TransferDateToString.transfer(date,"/");

        //формируем путь к XML с данными
        String realPath = path+"?date_req="+dataStr;

        try{
            parse(realPath);
        }catch(Exception ex){
            System.out.println("Ошибка при парсинге");
            ex.printStackTrace();
        }

    }

    private void parseParam(Node param,Currency currency,Rate rate){

        String paramName = param.getNodeName();
        String text = param.getChildNodes().item(0).getTextContent();

        switch (paramName){
            case "NumCode":{
                currency.setNumCode(text);
                break;
            }
            case "CharCode":{
                currency.setCharCode(text);
                break;
            }
            case "Nominal":{
                rate.setNominal(Integer.parseInt(text));
                break;
            }
            case "Name":{
                currency.setName(text);
                break;
            }
            case "Value":{
                rate.setValue(Double.parseDouble(text.replace(",",".")));
                break;
            }
        }

    }

    private String parseDate(Document doc){
        Node root = doc.getElementsByTagName("ValCurs").item(0);
        NamedNodeMap attributes = root.getAttributes();
        return attributes.getNamedItem("Date").getNodeValue();
    }

    private void parse(String path) throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(path);

        NodeList valuteElements = document.getDocumentElement().getElementsByTagName("Valute");

        //получаем дату
        String date = parseDate(document);

        //перебираем все Valute
        for (int i = 0;i<valuteElements.getLength();++i){

            Currency currency = new Currency();
            Rate rate = new Rate();

            try{
                rate.setDate(TransferDateToString.transferToDate(date,"dd.MM.yyyy"));
            }catch (ParseException exception){}

            Node valElem = valuteElements.item(i);

            NamedNodeMap attributes = valElem.getAttributes();
            currency.setValuteID(attributes.getNamedItem("ID").getNodeValue());

            NodeList valuteParams = valElem.getChildNodes();
            for(int j = 0;j<valuteParams.getLength();++j){
                Node param = valuteParams.item(j);
                if(param.getNodeType()==Node.ELEMENT_NODE){
                    parseParam(param,currency,rate);
                }
            }//for j

            rate.setCurrency(currency);

            currencyService.addCurrency(currency);
            rateService.addRate(rate);

        }//for i

    }

}
