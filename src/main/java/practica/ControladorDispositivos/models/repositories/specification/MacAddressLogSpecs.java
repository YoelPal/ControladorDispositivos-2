package practica.ControladorDispositivos.models.repositories.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import practica.ControladorDispositivos.models.entities.Dispositivo;
import practica.ControladorDispositivos.models.entities.MacAddressLog;

public class MacAddressLogSpecs {
    public static Specification<MacAddressLog> macContaining(String mac) {
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

    public static Specification<MacAddressLog> sedeContaining(String sede) {
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

    public static Specification<MacAddressLog> noCoincidentesPorMac() {
        return (root, query, cb) -> {
            Subquery<String> subQuery = query.subquery(String.class);
            jakarta.persistence.criteria.Root<Dispositivo> dispositivoRoot = subQuery.from(Dispositivo.class);
            subQuery.select(dispositivoRoot.get("macAddress")); // Asume que "macAddress" es el campo en Dispositivo

            Predicate macEnDispositivos = cb.in(root.get("macAddress")).value(subQuery); // Asume que "macAddress" es el campo en MacAddressLog

            return cb.not(macEnDispositivos); // Queremos las MACs que NO están en la subconsulta
        };
    }
}
