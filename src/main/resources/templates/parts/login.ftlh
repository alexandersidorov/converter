<#macro login path isRegisterForm>

<div class = "card text-center" style = "width:50rem;">

    <div class = "card-body">

        <#if isRegisterForm>
            <h5 class="card-title">Регистрация</h5>
        <#else>
            <h5 class="card-title">Вход</h5>
        </#if>

        <form action=${path} method="post">

             <div class="form-group row">
                <label class="col-sm-4 col-form-label">Имя пользователя:</label>
                <div class = "col-sm-6">
                    <input type="text" name="username" value="<#if user??>${user.username}</#if>"
                    class="form-control ${(usernameError??)?string('is-invalid', '')}" placeholder="Имя пользователя" />
                    <#if usernameError??>
                    <div class="invalid-feedback">
                    ${usernameError}
                    </div>
                    </#if>
                </div>
             </div>

             <div class="form-group row">
                <label class="col-sm-4 col-form-label">Пароль:</label>
                <div class = "col-sm-6">
                    <input type="password" name="password"
                    class="form-control ${(passwordError??)?string('is-invalid', '')}" placeholder="Пароль" />
                    <#if passwordError??>
                    <div class="invalid-feedback">
                    ${passwordError}
                    </div>
                    </#if>
                </div>
             </div>

              <#if isRegisterForm>
                     <div class="form-group row">
                         <label class="col-sm-4 col-form-label">Повторите пароль:</label>
                         <div class="col-sm-6">
                             <input type="password" name="password2"
                             class="form-control ${(password2Error??)?string('is-invalid', '')}"placeholder="Повторите пароль" />
                             <#if password2Error??>
                             <div class="invalid-feedback">
                             ${password2Error}
                             </div>
                             </#if>
                         </div>
                     </div>
              </#if>

            <input type="hidden" name="_csrf" value = ${_csrf.token} />

            <div>
                <button class = "btn btn-dark" type = "submit"><#if isRegisterForm>Создать<#else>Вход</#if></button>
            </div>

            <#if !isRegisterForm>
                <div><a href = "/registration">Регистрация</a></div>
            </#if>

        </form>

    </div>

</div>

</#macro>

<#macro logout>
<form action="/logout" method="post">
    <button class = "btn btn-dark" type = "submit">Выход</button>
    <input type="hidden" name="_csrf" value = ${_csrf.token} />
</form>
</#macro>