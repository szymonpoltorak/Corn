<#import "tailwind-template.ftl" as layout>
<#import "common.ftl" as common />

<@layout.mainLayout active='account' bodyClass='user'; section>

    <div class="flex justify-between items-center px-6 pb-4 border-b border-gray-300">
        <h2 class="text-3xl font-bold">${msg("editAccountHtmlTitle")}</h2>
        <div class="text-sm">
            <@common.RED_STAR />
            <span>${msg("requiredFields")}</span>
        </div>
    </div>

    <div class="pt-4">
        <form action="${url.accountUrl}" class="grid gap-2" method="post">

            <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">

            <#if !realm.registrationEmailAsUsername>
                <div>
                    <div>
                        <label for="username">${msg("username")}</label>
                        <@common.RED_STAR />
                        <#if messagesPerField.existsError('username')>
                            <span class="text-red-500">
                                ${kcSanitize(messagesPerField.getFirstError('username'))?no_esc}
                            </span>
                        </#if>
                    </div>

                    <@common.DARK_INPUT 
                        tabindex="0" type="text"
                        placeholder="${msg('username')}"
                        id="username" name="username"
                        value="${(account.username!'')}"
                        disabled="${(!(realm.editUsernameAllowed))?string('true', 'false')}"
                    />
                </div>
            </#if>

            <div>
                <div>
                    <label for="email">${msg("email")}</label>
                    <@common.RED_STAR />
                    <#if messagesPerField.existsError('email')>
                        <span class="text-red-500">
                            ${kcSanitize(messagesPerField.getFirstError('email'))?no_esc}
                        </span>
                    </#if>
                </div>
                <@common.DARK_INPUT 
                    tabindex="0" type="text"
                    placeholder="${msg('email')}"
                    id="email" name="email"
                    value="${(account.email!'')}"
                />
            </div>

            <div>
                <div>
                    <label for="firstName">${msg("firstName")}</label>
                    <@common.RED_STAR />
                    <#if messagesPerField.existsError('firstName')>
                        <span class="text-red-500">
                            ${kcSanitize(messagesPerField.getFirstError('firstName'))?no_esc}
                        </span>
                    </#if>
                </div>
                <@common.DARK_INPUT 
                    tabindex="0" type="text"
                    placeholder="${msg('firstName')}"
                    id="firstName" name="firstName"
                    value="${(account.firstName!'')}"
                />
            </div>

            <div>
                <div>
                    <label for="lastName">${msg("lastName")}</label>
                    <@common.RED_STAR />
                    <#if messagesPerField.existsError('lastName')>
                        <span class="text-red-500">
                            ${kcSanitize(messagesPerField.getFirstError('lastName'))?no_esc}
                        </span>
                    </#if>
                </div>
                <@common.DARK_INPUT 
                    tabindex="0" type="text"
                    placeholder="${msg('lastName')}"
                    id="lastName" name="lastName"
                    value="${(account.lastName!'')}"
                />
            </div>

            <div class="grid gap-4 pt-2">
                <@common.YELLOW_BUTTON tabindex="" type="submit" name="submitAction" id="kc-login" value="Save">
                    ${msg("doSave")}
				</@common.YELLOW_BUTTON>
                <@common.WHITE_BUTTON tabindex="" type="submit" name="submitAction" id="kc-login" value="Cancel">
                    ${msg("doCancel")}
				</@common.WHITE_BUTTON>
            </div>
        
        </form>
    </div>

</@layout.mainLayout>
