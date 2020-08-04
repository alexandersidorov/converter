<div class = "card text-center" style = "width:50rem;">

    <div class = "card-body">
        <h5 class="card-title">Конвертер валют</h5>

        <form method = "post" action = "/">
            <div class="form-row">
                <div class="form-group col-md-6">
                  <label for="fromName">Исходная валюта</label>
                  <select id="fromName" name = "fromName" class="form-control">
                    <option selected >${captionFrom}</option>
                    <#list captions as caption>
                        <option>${caption}</option>
                    </#list>
                  </select>
                </div>

                <div class="form-group col-md-6">
                  <label for="toName">Валюта конвертации</label>
                  <select id="toName" name = "toName" class="form-control">
                    <option selected>${captionTo}</option>
                    <#list captions as caption>
                        <option>${caption}</option>
                    </#list>
                  </select>
                </div>
            </div>

            <div class="form-row">
                <div class="form-group col-md-6">
                    <input type="text" class="form-control" id="fromCount" name = "fromCount" <#if count??>value = "${count}"</#if> >
                </div>

                <div class="form-group col-md-6">
                    <input type="text" class="form-control" id="toCount" name = "toCount" <#if result??>value = "${result}"</#if> >
                </div>
            </div>

             <div class="row">
                <div class="col text-center">
                    <button class="btn btn-dark" type = "submit">Конвертировать</button>
                </div>
             </div>

             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>

    </div>

</div>
