<#import "tailwind-template.ftl" as template />
<#import "common.ftl" as common />

<@template.mainLayout ; section>
	<#if section = "main">
		<div>
			<form id="kc-form-login" onsubmit="login.disabled = true; return true;" action="${url.loginAction}" method="post">

				<@common.USERNAME_INPUT tabindex="1" />

                <@common.SPACER />

				<@common.PASSWORD_INPUT tabindex="2" />
                
                <#if messagesPerField.existsError('username','password')>
                    <@common.MID_SPACER />
                    <p class="text-center text-lg text-red-500" id="email-error" aria-live="polite">
                        ${kcSanitize(messagesPerField.getFirstError('username','password'))?no_esc}
                    </p>
                    <@common.MID_SPACER />
                <#else>
                    <@common.SPACER />
                </#if>

				<div>
					<input type="hidden" id="id-hidden-input" name="credentialId" <#if auth.selectedCredential?has_content>value="${auth.selectedCredential}"</#if>>
					<@common.YELLOW_BUTTON tabindex="3" type="submit" name="login" id="kc-login">
                        ${msg("doLogIn")}
					</@common.YELLOW_BUTTON>
				</div>

				<#if realm.resetPasswordAllowed || realm.rememberMe>
					<div class="flex items-center justify-between mt-[2px]">
                        <#if realm.resetPasswordAllowed>
                            <div class="flex items-center">
                                <span class="text-white">
                                    ${msg("doForgotPassword")}
                                </span>
                                <a href="${url.loginResetCredentialsUrl}" tabindex="4" class="text-small text-yellow-300 font-bold ml-[2px]">
                                    ${msg("doClickHere")}
                                </a>
                            </div>
                        </#if>
						<#if realm.rememberMe>
							<div class="flex items-center">
								<input tabindex="5" id="rememberMe" name="rememberMe" type="checkbox" class="rounded">
								<label for="rememberMe" class="ml-[2px] text-small text-white">
									${msg("rememberMe")}
								</label>
							</div>
						</#if>
					</div>
				</#if>

				<#if realm.registrationAllowed>
                    <span class="text-white">
                        ${msg("noAccount")}
                    </span>
                    <a href="${url.registrationUrl}" tabindex="6" class="text-small text-yellow-300 font-bold ml-[2px]">
                        ${msg("doRegister")}
                    </a>
				</#if>
			</form>

			<@common.SOCIAL_PROVIDERS />

		</div>
    </#if>
</@template.mainLayout>
