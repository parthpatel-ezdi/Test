/**
 * 
 */
package com.ezdi.mt.worklist.application;

import com.ezdi.component.logger.EzdiLogManager;
import com.ezdi.component.logger.EzdiLogger;
import com.ezdi.mt.core.util.SolrCloudUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author EZDI\atul.r
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={CassandraDataAutoConfiguration.class})
@ComponentScan(basePackages={"com.ezdi.mt"})
@Import(SolrCloudUtil.class)
public class WorklistApplication {
    private static EzdiLogger LOG = EzdiLogManager.getLogger(WorklistApplication.class);
	public static void main(String[] args) 
	{
        LOG.info("Application starting");
		SpringApplication.run(WorklistApplication.class, args);
	}
}
