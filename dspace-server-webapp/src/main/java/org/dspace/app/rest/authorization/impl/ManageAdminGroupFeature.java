/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE and NOTICE files at the root of the source
 * tree and available online at
 *
 * http://www.dspace.org/license/
 */
package org.dspace.app.rest.authorization.impl;

import java.sql.SQLException;

import org.dspace.app.rest.authorization.AuthorizationFeature;
import org.dspace.app.rest.authorization.AuthorizationFeatureDocumentation;
import org.dspace.app.rest.model.BaseObjectRest;
import org.dspace.app.rest.model.CollectionRest;
import org.dspace.app.rest.utils.Utils;
import org.dspace.app.util.AuthorizeUtil;
import org.dspace.content.Collection;
import org.dspace.core.Context;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * The manage admin group feature. It can be used to verify if a collection admin group can be created, deleted,
 * viewed or edited.
 *
 * Authorization is granted if the current user has ADMIN permissions on the given collection and the configuration
 * allows the collection admin to manage admin groups, OR the current user has ADMIN permissions on the given
 * collection's owning community and the configuration allows the community admin to manage admin groups.
 */
@Component
@AuthorizationFeatureDocumentation(name = ManageAdminGroupFeature.NAME,
    description = "It can be used to verify if the collection admin group can be created, deleted," +
        " viewed or edited.")
public class ManageAdminGroupFeature implements AuthorizationFeature {

    public final static String NAME = "canManageAdminGroup";

    @Autowired
    private Utils utils;

    @Override
    public boolean isAuthorized(Context context, BaseObjectRest object) throws SQLException {
        if (object instanceof CollectionRest) {
            try {
                AuthorizeUtil.authorizeManageAdminGroup(context,
                    (Collection)utils.getDSpaceAPIObjectFromRest(context, object));
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public String[] getSupportedTypes() {
        return new String[]{
            CollectionRest.CATEGORY + "." + CollectionRest.NAME
        };
    }
}
