<#include "security.ftlh">
<#import "login.ftlh" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-secondary">

  <a class="navbar-brand" href="/">Конвертер</a>

  <button class="navbar-toggler" type="button" data-toggle="collapse"
   data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">

    <ul class="navbar-nav mr-auto">

      <#if user??>
          <li class="nav-item">
            <a class="nav-link" href="/journal/${currentUserId}">История</a>
          </li>
      </#if>

    </ul>

    <div class = "navbar-text mr-3">${name}</div>
    <#if user??>
        <@l.logout />
    </#if>

  </div>

</nav>