<#import "parts/common.ftlh" as c>

<@c.page>

<#if message??>
    <div>
        ${message}
    </div>
</#if>


<form method = "post" >
    <div class="form-row mb-4">

        <div class="form-group col-md-4">
            <label for="date">Дата операции</label>
            <input type="date" class="form-control" id="date" name="date" <#if date??>value = "${date}"</#if> placeholder="Дата" required>
        </div>

        <div class="form-group col-md-4">
            <label for="fromName">Исходная валюта</label>
            <select id="fromName" name = "fromName" class="form-control">
                <option selected>${captionFrom}</option>
                <#list captions as caption>
                    <option>${caption}</option>
                </#list>
            </select>
        </div>

        <div class="form-group col-md-4">
            <label for="toName">Валюта конвертации</label>
            <select id="toName" name = "toName" class="form-control">
                <option selected>${captionTo}</option>
                <#list captions as caption>
                    <option>${caption}</option>
                </#list>
            </select>
        </div>
    </div>

    <#include "parts/listConv.ftlh" />

    <div class="row">
        <div class="col text-center">
            <button class="btn btn-dark" type = "submit">Показать</button>
        </div>
    </div>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>



</@c.page>
