package practica.controladordispositivos.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import practica.controladordispositivos.models.dto.DispositivoDTO;
import practica.controladordispositivos.models.entities.Dispositivo;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        //Configuración de atributos particulare
        modelMapper.typeMap(Dispositivo.class, DispositivoDTO.class).addMappings(mapper ->{
            mapper.map(Dispositivo::getMacAddress,DispositivoDTO::setMacAddress);
        });

        return modelMapper;
    }
}
