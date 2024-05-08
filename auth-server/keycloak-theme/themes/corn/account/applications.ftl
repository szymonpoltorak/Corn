<#import "tailwind-template.ftl" as layout>
<#import "common.ftl" as common />

<@layout.mainLayout active='applications' bodyClass='applications'; section>

    <div class="flex justify-between items-center px-6 pb-4 border-b border-gray-300">
        <h2 class="text-3xl font-bold">${msg("applicationsHtmlTitle")}</h2>
    </div>

    <div class="pt-4">
        <form action="${url.applicationsUrl}" method="post">
            <input type="hidden" id="stateChecker" name="stateChecker" value="${stateChecker}">
            <input type="hidden" id="referrer" name="referrer" value="${stateChecker}">

            <table class="min-w-full divide-y divide-gray-200 bg-yellowishDark100">
                <thead>
                    <tr class="text-left text-xs font-medium text-gray-200 uppercase tracking-wider">
                        <th class="px-3 py-3">${msg("application")}</th>
                        <th class="px-3 py-3">${msg("availableRoles")}</th>
                        <th class="px-3 py-3">${msg("grantedPermissions")}</th>
                        <th class="px-3 py-3">${msg("additionalGrants")}</th>
                        <th class="px-3 py-3">${msg("action")}</th>
                    </tr>
                </thead>

                <tbody class="divide-y divide-gray-200">
                    <#list applications.applications as application>
                        <tr class="text-sm text-white">
                            <td class="px-3 py-4">
                                <#if application.effectiveUrl?has_content><a href="${application.effectiveUrl}"></#if>
                                    <#if application.client.name?has_content>${advancedMsg(application.client.name)}<#else>${application.client.clientId}</#if>
                                <#if application.effectiveUrl?has_content></a></#if>
                            </td>

                            <td class="px-3 py-4">
                                <#list application.realmRolesAvailable as role>
                                    <#if role.description??>${advancedMsg(role.description)}<#else>${advancedMsg(role.name)}</#if>
                                    <#if role_has_next>, </#if>
                                </#list>
                                <#list application.resourceRolesAvailable?keys as resource>
                                    <#if application.realmRolesAvailable?has_content>, </#if>
                                    <#list application.resourceRolesAvailable[resource] as clientRole>
                                        <#if clientRole.roleDescription??>${advancedMsg(clientRole.roleDescription)}<#else>${advancedMsg(clientRole.roleName)}</#if>
                                        ${msg("inResource")} <strong><#if clientRole.clientName??>${advancedMsg(clientRole.clientName)}<#else>${clientRole.clientId}</#if></strong>
                                        <#if clientRole_has_next>, </#if>
                                    </#list>
                                </#list>
                            </td>

                            <td class="px-3 py-4">
                                <#if application.client.consentRequired>
                                    <#list application.clientScopesGranted as claim>
                                        ${advancedMsg(claim)}<#if claim_has_next>, </#if>
                                    </#list>
                                <#else>
                                    <strong>${msg("fullAccess")}</strong>
                                </#if>
                            </td>

                            <td class="px-3 py-4">
                            <#list application.additionalGrants as grant>
                                    ${advancedMsg(grant)}<#if grant_has_next>, </#if>
                                </#list>
                            </td>

                            <td class="px-3 py-4">
                                <#if (application.client.consentRequired && application.clientScopesGranted?has_content) || application.additionalGrants?has_content>
                                    <button type='submit' class='${properties.kcButtonClass!} ${properties.kcButtonPrimaryClass!}' id='revoke-${application.client.clientId}' name='clientId' value="${application.client.id}">${msg("revoke")}</button>
                                </#if>
                            </td>
                        </tr>
                    </#list>
                </tbody>
            </table>
        </form>
    <div>

</@layout.mainLayout>
