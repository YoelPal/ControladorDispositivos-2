package practica.controladordispositivos.models.repositories.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class GenericSpecs {

    public static <T> Specification<T> macContaining(String mac) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(mac)) {
                return cb.like(
                        cb.lower(root.get("macAddress")),
                        "%" + mac.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

    public static <T> Specification<T> sedeContaining(String sede) {
        return (root, query, cb) -> {
            if (StringUtils.hasText(sede)) {
                return cb.like(
                        cb.lower(root.get("sede")),
                        "%" + sede.toLowerCase() + "%"
                );
            }
            return null;
        };
    }

}

