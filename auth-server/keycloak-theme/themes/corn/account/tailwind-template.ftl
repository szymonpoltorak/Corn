<#import "common.ftl" as common />

<#macro mainLayout active bodyClass>
<!doctype html>
<html>

    <head>
        <meta charset="utf-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="robots" content="noindex, nofollow">
        <#if properties.meta?has_content>
            <#list properties.meta?split(' ') as meta>
				<meta name="${meta?split('==')[0]}" content="${meta?split('==')[1]}"/>
			</#list>
		</#if>
		<title>
            ${msg("accountManagementTitle")}
        </title>
		<link rel="icon" href="${url.resourcesPath}/../../common/corn/img/corn.png" />
		<link rel="stylesheet" href="${url.resourcesPath}/../../common/corn/css/styles.css" />
        <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
        <style>
            body {
                font-family: 'Roboto', sans-serif;
            }
        </style> 
	</head>

<body class="flex flex-col h-screen bg-yellow-400">

    <header class="w-full bg-yellowishDark">
        <nav class="w-full flex justify-between items-center py-2 px-2">
            <div class="flex items-center">
                <img src="${url.resourcesPath}/../../common/corn/img/corn.png" class="h-8 mr-2">
                <h1 class="text-white font-bold text-lg">Corn</h1>
            </div>
            
            <div class="flex space-x-4 items-center whitespace-nowrap">
                <#if referrer?has_content && referrer.url?has_content>
                    <@common.WHITE_A_BUTTON href="${referrer.url}" id="kc-backto">
                        ${msg("backTo",referrer.name)}
                    </@common.WHITE_A_BUTTON>
                </#if>
                <@common.YELLOW_A_BUTTON href="${url.getLogoutUrl()}" id="kc-logout">
                    ${msg("doSignOut")}
                </@common.YELLOW_A_BUTTON>
            </div>
        </nav>
    </header>

    <div class="flex-grow pt-4">
        <div class="grid grid-cols-[196px,1fr] gap-4 mx-auto h-full max-w-[968px]">
            <ul class="space-y-2 py-4">
                <li>
                    <@common.ACTIVATABLE_BUTTON href="${url.accountUrl}" id="kc-account" expected="account" actual="${active}">
                        ${msg("account")}
                    </@common.ACTIVATABLE_BUTTON>
                </li>
                <#if features.passwordUpdateSupported>
                    <li>
                        <@common.ACTIVATABLE_BUTTON href="${url.passwordUrl}" id="kc-password" expected="password" actual="${active}">
                            ${msg("password")}
                        </@common.ACTIVATABLE_BUTTON>
                    </li>
                </#if>
                <li>
                    <@common.ACTIVATABLE_BUTTON href="${url.totpUrl}" id="kc-totp" expected="totp" actual="${active}">
                        ${msg("authenticator")}
                    </@common.ACTIVATABLE_BUTTON>
                </li>
                <#if features.identityFederation>
                    <li>
                        <@common.ACTIVATABLE_BUTTON href="${url.socialUrl}" id="kc-social" expected="social" actual="${active}">
                            ${msg("federatedIdentity")}
                        </@common.ACTIVATABLE_BUTTON>
                    </li>
                </#if>
                <li>
                    <@common.ACTIVATABLE_BUTTON href="${url.sessionsUrl}" id="kc-sessions" expected="sessions" actual="${active}">
                        ${msg("sessions")}
                    </@common.ACTIVATABLE_BUTTON>
                </li>
                <li>
                    <@common.ACTIVATABLE_BUTTON href="${url.applicationsUrl}" id="kc-applications" expected="applications" actual="${active}">
                        ${msg("applications")}
                    </@common.ACTIVATABLE_BUTTON>
                </li>
                <#if features.log>
                    <li>
                        <@common.ACTIVATABLE_BUTTON href="${url.logUrl}" id="kc-log" expected="log" actual="${active}">
                            ${msg("log")}
                        </@common.ACTIVATABLE_BUTTON>
                    </li>
                </#if>
                <#if realm.userManagedAccessAllowed && features.authorization>
                    <li>
                        <@common.ACTIVATABLE_BUTTON href="${url.resourceUrl}" id="kc-authorization" expected="authorization" actual="${active}">
                            ${msg("myResources")}
                        </@common.ACTIVATABLE_BUTTON>
                    </li>
                </#if>
            </ul>

            <div class="content-area bg-yellowishDark text-white h-full px-6 py-4 border border-gray-300 border-solid border-2 rounded-lg">
                <#if message?has_content>
                    <div>
                        <#if message.type=='success' >
                            <span class="text-green-500">${kcSanitize(message.summary)?no_esc}</span>
                        </#if>
                        <#if message.type=='error' >
                            <span class="text-red-500">${kcSanitize(message.summary)?no_esc}</span>
                        </#if>
                    </div>
                </#if>
                <#nested "content">
            </div>
        </div>
    </div>

</body>
</html>
</#macro>