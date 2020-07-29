<#macro add isUpdate>
    <a class="btn btn-primary" data-toggle="collapse"
       href="#collapseExample" role="button" aria-expanded="false"
       aria-controls="collapseExample">
        <#if isUpdate>Message editor<#else>New message</#if>
    </a>
    <div class="collapse <#if message??>show</#if>" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <input type="text" class="form-control ${(textError??)?string('is-invalid', '')} mt-3"
                           value="<#if message??>${message.text}</#if>" name="text" placeholder="Input message"/>
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control ${(tagError??)?string('is-invalid', '')}"
                           value="<#if message??>${message.tag}</#if>" name="tag" placeholder="Tag"/>
                    <#if tagError??>
                        <div class="invalid-feedback">
                            ${tagError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="inputGroupFileAddon01">Upload</span>
                        </div>
                        <div class="custom-file">
                            <input type="file" name="file" class="custom-file-input" id="inputGroupFile01"
                                   aria-describedby="inputGroupFileAddon01"/>
                            <label class="custom-file-label" for="inputGroupFile01">Choose file</label>
                        </div>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <#if isUpdate>
                    <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
                </#if>
                <div class="form-group">
                    <button type="submit" class="btn btn-primary"><#if isUpdate>Save<#else>Add</#if></button>
                </div>
            </form>
        </div>
    </div>
</#macro>