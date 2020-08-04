
<table class = "table table-bordered">
    <thead class = "thead-dark">
        <tr>
            <th>Исходная валюта</th>
            <th>Целевая валюта</th>
            <th>Исходная сумма</th>
            <th>Полученная сумма</th>
            <th>Дата</th>
        </tr>
    </thead>

    <tbody>
        <#list records as record>
            <tr>
                <td>${record.fullCaptionFrom}</td>
                <td>${record.fullCaptionTo}</td>
                <td>${record.valueFrom}</td>
                <td>${record.valueTo}</td>
                <td>${record.date}</td>
            </tr>

        </#list>
    </tbody>
</table>