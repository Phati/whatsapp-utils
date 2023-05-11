package io.github.phati.whatsapputils.autoconfigure;

import io.github.phati.whatsapputils.service.WhatsAppUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(WhatsAppUtils.class)
public class WhatsAppUtilAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(WhatsAppUtils.class)
    public WhatsAppUtils whatsAppUtils() {
        return new WhatsAppUtils();
    }
}
