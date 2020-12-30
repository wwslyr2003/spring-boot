package flow;

import java.io.File;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.messaging.MessageChannel;

@Configuration
public class FileWriterIntegrationConfig {

	/**
	 * xml 配置
	 *
	 */
	@Profile("xmlconfig") // 活动属性
	@Configuration
	@ImportResource("classpath:/filewriter-config.xml")
	public static class XmlConfiguration {
	}

	/**
	 * java配置
	 */
	@Profile("javaconfig")
	@Bean
	@Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
	public GenericTransformer<String, String> upperCaseTransformer() {
		return text -> text.toUpperCase();
	}

	@Profile("javaconfig")
	@Bean
	@ServiceActivator(inputChannel = "fileWriterChannel")
	public FileWritingMessageHandler fileWriter() {
		File file = new File("D://git");
		System.out.println(file.getAbsolutePath());
		FileWritingMessageHandler handler = new FileWritingMessageHandler(file);
		handler.setExpectReply(false);
		handler.setFileExistsMode(FileExistsMode.APPEND);
		handler.setAppendNewLine(true);
		return handler;
	}

	/**
	 * DSL 配置  
	 */
	@Profile("javadsl") 
	@Bean
	public IntegrationFlow fileWriterFlow() {
		return IntegrationFlows.from(MessageChannels.direct("textInChannel"))
				.<String, String>transform(t -> t.toUpperCase())
				.handle(Files.outboundAdapter(new File("D://git")).fileExistsMode(FileExistsMode.APPEND)
						.appendNewLine(true))
				.get();
	}

}
