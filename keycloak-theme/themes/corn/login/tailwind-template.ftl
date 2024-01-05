<#macro mainLayout>
<!DOCTYPE html>
<html class="">

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
            ${msg("loginTitle",(realm.displayName!''))}
        </title>
		<link rel="icon" href="${url.resourcesPath}/img/favicon.ico" />
		<link rel="stylesheet" href="${url.resourcesPath}/css/styles.css" />
	</head>
        
    <body class="h-screen flex flex-col bg-yellow-400">
        <div class="flex">
            <div class="ml-auto h-screen">
                <div class="max-h-[0px] max-w-[0px]">
                    <div class="min-h-screen flex items-center">
                        <img class="min-h-[512px] min-w-[512px] ml-[-352px]" src="${url.resourcesPath}/img/corn.png" />
                    </div>
                </div>
                <div class="bg-yellowishDark rounded-l-[32px] w-[160px] h-screen"></div>
            </div>
            <div class="bg-yellowishDark h-screen pl-[32px] pr-[32px] w-[50%]">
                <div class="h-screen flex items-center relative">
                    <div>
                        <h1 class="mt-[12px] mb-[12px] text-center font-semibold text-white text-5xl">
                            ${kcSanitize(msg("loginTitleHtml",(realm.displayNameHtml!'')))?no_esc}
                        </h1>
                        <#nested "main">
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>
</#macro>
