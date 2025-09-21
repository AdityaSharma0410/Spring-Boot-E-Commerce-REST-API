package com.etherealcart.backend.factoryregistry;

import com.etherealcart.backend.dto.ApiError;
import com.etherealcart.backend.exceptions.*;
import com.etherealcart.backend.factories.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ErrorResponseFactoryRegistry {

    private final Map<Class<? extends Exception>, ErrorResponseFactory> factories = new HashMap<>();
    @Autowired
    public ErrorResponseFactoryRegistry(
            DuplicateEmailErrorFactory duplicateEmailFactory,
            InvalidUserIdErrorFactory invalidUserIdFactory,
            UserNotFoundErrorFactory userNotFoundFactory,
            MissingRequiredFieldsErrorFactory missingFieldsFactory,
            InvalidEmailFormatErrorFactory invalidEmailFactory,
            InvalidProductIdFactory invalidProductIdFactory,
            ProductNotFoundFactory productNotFoundFactory,
            MissingProductFieldsFactory missingProductFieldsFactory,
            InvalidCategoryIdFactory invalidCategoryIdFactory,
            CategoryNotFoundFactory categoryNotFoundFactory,
            MissingCategoryFieldsFactory missingCategoryFieldsFactory,
            DuplicateCategorySlugErrorFactory duplicateCategorySlugFactory,
            InvalidProductPriceFactory invalidProductPriceFactory   // <--- add here
    ) {
        factories.put(DuplicateEmailException.class, duplicateEmailFactory);
        factories.put(InvalidUserIdException.class, invalidUserIdFactory);
        factories.put(UserNotFoundException.class, userNotFoundFactory);
        factories.put(MissingRequiredFieldsException.class, missingFieldsFactory);
        factories.put(InvalidEmailFormatException.class, invalidEmailFactory);
        factories.put(InvalidProductIdException.class, invalidProductIdFactory);
        factories.put(ProductNotFoundException.class, productNotFoundFactory);
        factories.put(MissingProductFieldsException.class, missingProductFieldsFactory);
        factories.put(InvalidCategoryIdException.class, invalidCategoryIdFactory);
        factories.put(CategoryNotFoundException.class, categoryNotFoundFactory);
        factories.put(MissingCategoryFieldsException.class, missingCategoryFieldsFactory);
        factories.put(DuplicateCategorySlugException.class, duplicateCategorySlugFactory);
        factories.put(InvalidProductPriceException.class, invalidProductPriceFactory); // <--- register
    }

    public ApiError createErrorResponse(Exception ex, HttpServletRequest request) {
        ErrorResponseFactory factory = factories.getOrDefault(
                ex.getClass(),
                (e, r) -> new ApiError(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        e.getMessage(),
                        r.getRequestURI()
                )
        );
        return factory.createError(ex, request);
    }
}
