<#import "tailwind-template.ftl" as template />
<#import "common.ftl" as common />

<@template.mainLayout ; section>
	<#if section = "main">
        <form id="kc-register-form" class="${properties.kcFormClass!}" action="${url.registrationAction}" method="post">

            <div class="grid grid-cols-2 gap-[12px]">
                <@common.FIRST_NAME_INPUT tabindex="1" />
                <@common.LAST_NAME_INPUT tabindex="2" />
            </div>

            <@common.SPACER />

			<@common.USERNAME_INPUT tabindex="3" />

            <#if !realm.registrationEmailAsUsername>
                <@common.SPACER />
			    <@common.EMAIL_INPUT tabindex="4" />
            </#if>

            <@common.SPACER />

			<@common.PASSWORD_INPUT tabindex="5" />

            <@common.SPACER />

			<@common.PASSWORD_CONFIRM_INPUT tabindex="6" />

            <@common.SPACER />

			<div>
				<input type="hidden" id="id-hidden-input" name="credentialId" <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>>
				<@common.YELLOW_BUTTON tabindex="7" type="submit" name="login" id="kc-login">
                    ${msg("doRegister")}
				</@common.YELLOW_BUTTON>
			</div>

			<div class="flex items-center justify-between mt-[2px]">
                <div class="flex items-center">
                    <a href="${url.loginUrl}" tabindex="8" class="text-small text-yellow-300 font-bold ml-[2px]">
                        ${kcSanitize(msg("backToLogin"))?no_esc}
                    </a>
                </div>
			</div>

			<@common.SOCIAL_PROVIDERS />

        </form>
    </#if>
</@template.mainLayout>
