<#import "parts/common.ftl" as c>
<#import "parts/messageAdd.ftl" as p>
<@c.page>
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="/main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter!}"
                       placeholder="Search by tag" />
                <button type="submit" class="btn btn-primary ml-2">Find</button>
            </form>
        </div>
    </div>
    <@p.add false/>
    <#include "parts/messageList.ftl" />
</@c.page>
