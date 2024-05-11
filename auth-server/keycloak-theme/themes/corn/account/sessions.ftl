<#import "tailwind-template.ftl" as layout>
<#import "common.ftl" as common />

<@layout.mainLayout active='sessions' bodyClass='sessions'; section>

    <div class="flex justify-between items-center px-6 pb-4 border-b border-gray-300">
        <h2 class="text-3xl font-bold">${msg("sessionsHtmlTitle")}</h2>
    </div>

    <div class="pt-4">
        <table class="min-w-full divide-y divide-gray-200 bg-yellowishDark100">
            <thead>
                <tr class="text-left text-xs font-medium text-gray-200 uppercase tracking-wider">
                    <th class="px-3 py-3">${msg("ip")}</th>
                    <th class="px-3 py-3">${msg("started")}</th>
                    <th class="px-3 py-3">${msg("lastAccess")}</th>
                    <th class="px-3 py-3">${msg("expires")}</th>
                    <th class="px-3 py-3">${msg("clients")}</th>
                </tr>
            </thead>
            <tbody class="divide-y divide-gray-200">
                <#list sessions.sessions as session>
                    <tr class="text-white">
                        <td class="px-3 py-4">${session.ipAddress}</td>
                        <td class="px-3 py-4">${session.started?datetime}</td>
                        <td class="px-3 py-4">${session.lastAccess?datetime}</td>
                        <td class="px-3 py-4">${session.expires?datetime}</td>
                        <td class="px-3 py-4 space-y-1">
                            <#list session.clients as client>
                                <span class="inline-block bg-gray-200 rounded-full px-3 py-1 text-sm font-semibold text-gray-700 mr-2">${client}</span>
                            </#list>
                        </td>
                    </tr>
                </#list>
            </tbody>
        </table>

        <form action="${url.sessionsUrl}" method="post" class="pt-4">
            <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">
            <@common.YELLOW_BUTTON tabindex="" type="submit" name="" id="logout-all-sessions" value="">
                ${msg("doLogOutAllSessions")}
            </@common.YELLOW_BUTTON>
        </form>
    <div>

</@layout.mainLayout>
