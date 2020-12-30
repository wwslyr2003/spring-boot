package flow;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/**
 * 
 * @author 武伟硕wws
 *网关
 */
@MessagingGateway(defaultRequestChannel = "textInChannel")
public interface FileWriterGateway {
	//读取文件
	void writeToFile(@Header(FileHeaders.FILENAME) String filename, String data);

}
