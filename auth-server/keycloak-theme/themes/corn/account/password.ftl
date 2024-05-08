<#import "tailwind-template.ftl" as layout>
<#import "common.ftl" as common />

<@layout.mainLayout active='password' bodyClass='password'; section>

    <div class="flex justify-between items-center px-6 pb-4 border-b border-gray-300">
        <h2 class="text-3xl font-bold">${msg("changePasswordHtmlTitle")}</h2>
        <div class="text-sm">
            <span>${msg("allFieldsRequired")}</span>
        </div>
    </div>

    <div class="pt-4">
        <form action="${url.passwordUrl}" class="grid gap-2" method="post">

            <input type="text" id="username" name="username" value="${(account.username!'')}" autocomplete="username" readonly="readonly" style="display:none;">

            <div>
                <div>
                    <label for="password">${msg("password")}</label>
                </div>
                <@common.DARK_INPUT 
                    tabindex="0" type="password"
                    placeholder=""
                    id="password" name="password"
                />
            </div>

            <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">

            <div>
                <div>
                    <label for="password-new">${msg("passwordNew")}</label>
                </div>
                <@common.DARK_INPUT 
                    tabindex="0" type="password"
                    placeholder=""
                    id="password-new" name="password-new"
                />
            </div>

            <div>
                <div>
                    <label for="password-confirm">${msg("passwordConfirm")}</label>
                </div>
                <@common.DARK_INPUT 
                    tabindex="0" type="password"
                    placeholder=""
                    id="password-confirm" name="password-confirm"
                />
            </div>

            <div class="pt-4">
                <@common.YELLOW_BUTTON tabindex="" type="submit" name="submitAction" id="kc-login" value="Save">
                    ${msg("doSave")}
                </@common.YELLOW_BUTTON>
            </div>

        </form>
    </div>

</@layout.mainLayout>
