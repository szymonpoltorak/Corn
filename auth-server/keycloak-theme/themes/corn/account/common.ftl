<#macro SPACER>
    <div class="h-8"></div>
</#macro>

<#macro MID_SPACER>
    <div class="h-4"></div>
</#macro>

<#macro LINE_TEXT_LINE textParam>
    <div class="flex items-center">
        <div class="flex-1 h-px bg-gray-300"></div>
        <div class="px-4 text-gray-300">${textParam}</div>
        <div class="flex-1 h-px bg-gray-300"></div>
    </div>
</#macro>

<#macro WHITE_A_BUTTON href id>
    <a 
        href="${href}" id="${id}"
        class=" w-full inline-flex justify-center px-4 py-2 bg-white border border-gray-400
                rounded-md text-gray-700 transition-colors duration-200 hover:bg-gray-300 hover:text-gray-800"
    >
        <#nested>
    </a>
</#macro>

<#--  edited YELLOW_BUTTON -->
<#macro YELLOW_BUTTON tabindex type name id value>
    <button 
        tabindex="${tabindex}" type="${type}"
        id="${name}" name="${id}"
        value="${value}"
        class=" w-full inline-flex justify-center px-4 py-2 bg-yellow-500 border border-yellow-600
                rounded-md text-white transition-colors duration-300 hover:bg-yellow-600 hover:border-yellow-700"
    >
        <#nested>
    </button>
</#macro>

<#--  edited DARK_INPUT -->
<#macro DARK_INPUT tabindex id name type placeholder value="" disabled="false">
    <#if disabled=="true">
        <input
            tabindex="${tabindex}" type="${type}"
            id="${id}" name="${name}"
            placeholder="${placeholder}"
            autocomplete="on"
            <#if value!="">value="${value}"</#if>
            disabled="disabled"
            class=" w-full bg-gray-400 border border-gray-400 rounded-md text-gray-500
                    placeholder-gray-500 cursor-not-allowed"
        />
    <#else>
        <input
            tabindex="${tabindex}" type="${type}"
            id="${id}" name="${name}"
            placeholder="${placeholder}"
            autocomplete="on"
            value="${value}"
            class=" w-full bg-gray-600 border border-gray-600 rounded-md text-white
                    placeholder-gray-200 focus:outline-none focus:border-blue-500"
        />
    </#if>
</#macro>

<#macro GITHUB_ICON>
    <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" aria-hidden="true">
        <path fill-rule="evenodd" d="M10 0C4.477 0 0 4.484 0 10.017c0 4.425 2.865 8.18 6.839 9.504.5.092.682-.217.682-.483 0-.237-.008-.868-.013-1.703-2.782.605-3.369-1.343-3.369-1.343-.454-1.158-1.11-1.466-1.11-1.466-.908-.62.069-.608.069-.608 1.003.07 1.531 1.032 1.531 1.032.892 1.53 2.341 1.088 2.91.832.092-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.988 1.029-2.688-.103-.253-.446-1.272.098-2.65 0 0 .84-.27 2.75 1.026A9.564 9.564 0 0110 4.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.027 2.747-1.027.546 1.379.203 2.398.1 2.651.64.7 1.028 1.595 1.028 2.688 0 3.848-2.339 4.695-4.566 4.942.359.31.678.921.678 1.856 0 1.338-.012 2.419-.012 2.747 0 .268.18.58.688.482A10.019 10.019 0 0020 10.017C20 4.484 15.522 0 10 0z" clip-rule="evenodd" />
    </svg>
</#macro>

<#macro GOOGLE_ICON>
    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-google" viewBox="0 0 16 16">
        <path d="M15.545 6.558a9.42 9.42 0 0 1 .139 1.626c0 2.434-.87 4.492-2.384 5.885h.002C11.978 15.292 10.158 16 8 16A8 8 0 1 1 8 0a7.689 7.689 0 0 1 5.352 2.082l-2.284 2.284A4.347 4.347 0 0 0 8 3.166c-2.087 0-3.86 1.408-4.492 3.304a4.792 4.792 0 0 0 0 3.063h.003c.635 1.893 2.405 3.301 4.492 3.301 1.078 0 2.004-.276 2.722-.764h-.003a3.702 3.702 0 0 0 1.599-2.431H8v-3.08h7.545z"/>
    </svg> 
</#macro>

<#macro SOCIAL_PROVIDERS>
    <#if realm.password && social.providers??>
        <@common.SPACER />
        <@common.LINE_TEXT_LINE textParam="or" />
        <@common.SPACER />
    	<div class="grid grid-cols-2 gap-[12px]">
    		<#list social.providers as p>
                <@common.WHITE_A_BUTTON href="${p.loginUrl}" id="social-${p.alias}">
                 	<#if p.providerId = "github">
                        <@common.GITHUB_ICON />
    				<#elseif p.providerId = "google">
                        <@common.GOOGLE_ICON />
    				</#if>
    				<span class="ml-[4px]">Login with ${p.displayName!}</span>
                </@common.WHITE_A_BUTTON>
    		</#list>
    	</div>
    </#if>
</#macro>

<#macro FIRST_NAME_INPUT tabindex>
    <@common.DARK_INPUT 
        tabindex="${tabindex}" type="text"
        placeholder="${msg(\"firstName\")}"
        id="firstName" name="firstName"
        value="${(firstName!'')}"
    />
</#macro>

<#macro LAST_NAME_INPUT tabindex>
    <@common.DARK_INPUT 
        tabindex="${tabindex}" type="text"
        placeholder="${msg(\"lastName\")}"
        id="lastName" name="lastName"
        value="${(lastName!'')}"
    />
</#macro>

<#macro USERNAME_INPUT tabindex>
    <@common.DARK_INPUT 
        tabindex="${tabindex}" type="text"
        placeholder="${msg(\"username\")}"
        id="username" name="username"
        value="${(username!'')}"
    />
</#macro>

<#macro EMAIL_INPUT tabindex>
    <@common.DARK_INPUT 
        tabindex="${tabindex}" type="text"
        placeholder="${msg(\"email\")}"
        id="email" name="email"
        value="${(email!'')}"
    />
</#macro>

<#macro PASSWORD_INPUT tabindex>
    <@common.DARK_INPUT 
        tabindex="${tabindex}" type="password"
        placeholder="${msg(\"password\")}"
        id="password" name="password"
        value="${(password!'')}"
    />
</#macro>

<#macro PASSWORD_CONFIRM_INPUT tabindex>
    <@common.DARK_INPUT 
        tabindex="${tabindex}" type="password"
        placeholder="${msg(\"passwordConfirm\")}"
        id="password-confirm" name="password-confirm"
        value="${(passwordConfirm!'')}"
    />
</#macro>

<#--  new YELLOW_A_BUTTON -->
<#macro YELLOW_A_BUTTON href id>
    <a 
        href="${href}" id="${id}"
        class=" w-full inline-flex justify-center px-4 py-2 bg-yellow-500 border border-yellow-600
                rounded-md text-white transition-colors duration-300 hover:bg-yellow-600 hover:border-yellow-700"
    >
        <#nested>
    </a>
</#macro>

<#--  new CORNACCENT_A_BUTTON -->
<#macro CORNACCENT_A_BUTTON href id>
    <a 
        href="${href}" id="${id}"
        class=" w-full inline-flex justify-center px-4 py-2 bg-corn-accent-500 border border-corn-accent-600
                rounded-md text-white transition-colors duration-300 hover:bg-corn-accent-600 hover:border-corn-accent-700"
    >
        <#nested>
    </a>
</#macro>

<#--  new ACTIVATABLE_BUTTON -->
<#macro ACTIVATABLE_BUTTON href id expected actual>
    <#if "${expected}"=="${actual}">
        <@CORNACCENT_A_BUTTON href="#" id="${id}">
            <#nested>
        </@CORNACCENT_A_BUTTON>
    <#else>
        <@WHITE_A_BUTTON href="${href}" id="${id}">
            <#nested>
        </@WHITE_A_BUTTON>
    </#if>
</#macro>

<#--  new RED_STAR -->
<#macro RED_STAR>
    <span class="text-red-500 text-sm">*</span>
</#macro>

<#--  new WHITE_BUTTON -->
<#macro WHITE_BUTTON tabindex type name id value>
    <button 
        tabindex="${tabindex}" type="${type}"
        id="${name}" name="${id}"
        value="${value}"
        class=" w-full inline-flex justify-center px-4 py-2 bg-white border border-gray-400
                rounded-md text-gray-700 transition-colors duration-200 hover:bg-gray-300 hover:text-gray-800"
    >
        <#nested>
    </button>
</#macro>
