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
		<link rel="icon" href="${url.resourcesPath}/../../corn/img/corn.png" />
		<link rel="stylesheet" href="${url.resourcesPath}/../../common/corn/css/styles.css" />
        <link href='http://fonts.googleapis.com/css?family=Roboto' rel='stylesheet' type='text/css'>
        <style>
            body {
                font-family: 'Roboto', sans-serif;
            }
        </style> 
    </head>
        
    <body class="h-screen flex flex-col bg-yellow-400">
        <div class="flex">
            <div class="ml-auto h-screen hidden lg:block">
                <div class="max-h-[0px] max-w-[0px]">
                    <div class="min-h-screen flex items-center">
                        <img class="min-h-[472px] min-w-[472px] ml-[-312px] xl:min-h-[512px] xl:min-w-[512px] xl:ml-[-352px]"
                            src="${url.resourcesPath}/../../common/corn/img/corn.png" />
                    </div>
                </div>
                <div class="bg-yellowishDark rounded-l-[32px] w-[160px] h-screen"></div>
            </div>
            <div class="bg-yellowishDark h-screen pl-[32px] pr-[32px] w-full lg:w-[50%]">
                <div class="h-screen flex items-center relative">
                    <div class="w-full xl:w-[0px] xl:min-w-[512px]">
                        <div class="flex mt-[12px] mb-[12px] text-center font-semibold text-white text-5xl">
                            <div class="flex mx-auto space-x-[16px]">
                                <img class="lg:hidden h-[40px] w-[40px] my-auto" src="${url.resourcesPath}/../../common/corn/img/corn.png" />
                                <h1>
                                    ${kcSanitize(msg("loginTitleHtml",(realm.displayNameHtml!'')))?no_esc}
                                </h1>
                            </div>
                        </div>
                        <#nested "main">
                    </div>
                </div>
            </div>
        </div>
    </body>

</html>
</#macro>
