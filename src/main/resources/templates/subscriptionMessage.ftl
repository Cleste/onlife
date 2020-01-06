<#import "parts/common.ftl" as c>
<@c.page>
    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <#include "parts/messageList.ftl" />
</@c.page>