package practica.ControladorDispositivos.models.repositories.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.entities.MacAddressLog;

public class DispositivosSpecs {
        public static Specification<Dispositivo> macContaining(String mac) {
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

        public static Specification<Dispositivo> sedeContaining(String sede) {
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
