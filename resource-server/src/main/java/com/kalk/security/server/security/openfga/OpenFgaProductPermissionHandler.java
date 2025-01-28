package com.kalk.security.server.security.openfga;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import com.kalk.security.server.entity.Product;
import com.kalk.security.server.security.CustomPermissionEvaluator;
import com.kalk.security.server.security.PermissionUpdateHandler;
import dev.openfga.sdk.api.client.OpenFgaClient;
import dev.openfga.sdk.api.client.model.ClientCheckRequest;
import dev.openfga.sdk.api.client.model.ClientTupleKey;
import dev.openfga.sdk.api.client.model.ClientWriteRequest;
import dev.openfga.sdk.errors.FgaInvalidParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Profile("openfga")
public class OpenFgaProductPermissionHandler implements CustomPermissionEvaluator, PermissionUpdateHandler {

    private static final Logger LOG = LoggerFactory.getLogger(OpenFgaProductPermissionHandler.class);

    private final OpenFgaClient client;

    public OpenFgaProductPermissionHandler(OpenFgaClient openFgaClient) {
        this.client = openFgaClient;
    }

    @Override
    public void create(Object entity) {
        Product product = (Product) entity;

        var body = new ClientWriteRequest()
                .writes(List.of(
                        createOwnerTuple(product),
                        createParentTuple(product)
                ));

        try {
            client.write(body).get();
        } catch (InterruptedException | ExecutionException | FgaInvalidParameterException e) {
            throw new RuntimeException(e);
        }
    }

    private ClientTupleKey createOwnerTuple(Product product) {
        return new ClientTupleKey()
                .user("user:" + product.getCreator())
                .relation("owner")
                ._object("product:" + product.getId());
    }

    private ClientTupleKey createParentTuple(Product product) {
        return new ClientTupleKey()
                .user("product_group:" + product.getProductGroup().getId())
                .relation("parent")
                ._object("product:" + product.getId());
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (targetDomainObject instanceof Product product) {
            return hasPermission(authentication, product.getId(), "product", permission);
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        var body = new ClientCheckRequest()
                .user("user:" + authentication.getName())
                .relation(permission.toString().toLowerCase(Locale.ROOT))
                ._object(targetType.toLowerCase(Locale.ROOT) + ":" + targetId.toString());

        try {
            var response = client.check(body).get();
            return Boolean.TRUE.equals(response.getAllowed());
        } catch (InterruptedException | ExecutionException | FgaInvalidParameterException e) {
            LOG.warn(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean canHandle(Object object) {
        return Product.class.isAssignableFrom(object.getClass());
    }
}
