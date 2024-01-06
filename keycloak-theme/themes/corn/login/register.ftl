<#import "tailwind-template.ftl" as template />
<#import "common.ftl" as common />

<@template.mainLayout ; section>
	<#if section = "main">
        <form id="kc-register-form" class="${properties.kcFormClass!}" action="${url.registrationAction}" method="post">

            <div class="grid grid-cols-2 gap-[12px]">
                <div>
                    <@common.FIRST_NAME_INPUT tabindex="1" />
                    <#if messagesPerField.existsError('firstName')>
                        <p class="text-center text-lg text-red-500" id="firstName-error" aria-live="polite">
                            ${kcSanitize(messagesPerField.getFirstError('firstName'))?no_esc}
                        </p>
                        <@common.MID_SPACER />
                    <#else>
                        <@common.SPACER />
                    </#if>
                </div>
                <div>
                    <@common.LAST_NAME_INPUT tabindex="2" />
                    <#if messagesPerField.existsError('lastName')>
                        <p class="text-center text-lg text-red-500" id="lastName-error" aria-live="polite">
                            ${kcSanitize(messagesPerField.getFirstError('lastName'))?no_esc}
                        </p>
                        <@common.MID_SPACER />
                    <#else>
                        <@common.SPACER />
                    </#if>
                </div>
            </div>

			<@common.USERNAME_INPUT tabindex="3" />
            <#if messagesPerField.existsError('username')>
                <p class="text-center text-lg text-red-500" id="username-error" aria-live="polite">
                    ${kcSanitize(messagesPerField.getFirstError('username'))?no_esc}
                </p>
                <@common.MID_SPACER />
            <#else>
                <@common.SPACER />
            </#if>

            <#if !realm.registrationEmailAsUsername>
			    <@common.EMAIL_INPUT tabindex="4" />
                <#if messagesPerField.existsError('email')>
                    <p class="text-center text-lg text-red-500" id="email-error" aria-live="polite">
                        ${kcSanitize(messagesPerField.getFirstError('email'))?no_esc}
                    </p>
                    <@common.MID_SPACER />
                <#else>
                    <@common.SPACER />
                </#if>
            </#if>


			<@common.PASSWORD_INPUT tabindex="5" />
            <#if messagesPerField.existsError('password')>
                <p class="text-center text-lg text-red-500" id="password-error" aria-live="polite">
                    ${kcSanitize(messagesPerField.getFirstError('password'))?no_esc}
                </p>
                <@common.MID_SPACER />
            <#else>
                <@common.SPACER />
            </#if>

			<@common.PASSWORD_CONFIRM_INPUT tabindex="6" />
            <#if messagesPerField.existsError('password-confirm')>
                <p class="text-center text-lg text-red-500" id="password-confirm-error" aria-live="polite">
                    ${kcSanitize(messagesPerField.getFirstError('password-confirm'))?no_esc}
                </p>
                <@common.MID_SPACER />
            <#else>
                <@common.SPACER />
            </#if>

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
