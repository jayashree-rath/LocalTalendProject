
package local_project.restapi_demo_cloudwatch_0_1;

//import java.util.List ;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.camel.CamelContext;
import routines.*;
import routines.TalendString;
import routines.system.*;
import routines.system.api.TalendESBRoute;

/**
 * Job: RestAPI_Demo_Cloudwatch Purpose: <br>
 * Description: <br>
 * 
 * @author jayashree.rath@keyrus.com
 * @version 7.3.1.20210623_0656-patch
 * @status
 */

public class RestAPI_Demo_Cloudwatch extends org.apache.camel.builder.RouteBuilder implements TalendESBRoute {

	private boolean inOSGi = routines.system.BundleUtils.inOSGi();

	private boolean inMS = false;

	private static Object arguments;

	public void setArguments(Object arguments) {
		this.inOSGi = true;
		RestAPI_Demo_Cloudwatch.arguments = arguments;

		if (null != context && null != context.getProperty("context")) {
			contextStr = (String) context.getProperty("context");
		}

	}

	public <T> Map<String, T> getArguments(Object props, Class c) {
		if (props != null) {
			try {
				java.lang.reflect.Field propertiesField = props.getClass().getDeclaredField("properties");
				propertiesField.setAccessible(true);
				java.util.Dictionary p = (java.util.Dictionary) propertiesField.get(props);
				java.util.Map<String, T> result = new java.util.HashMap<>();

				if (p != null) {
					for (java.util.Enumeration<String> keys = p.keys(); keys.hasMoreElements();) {
						String key = keys.nextElement();
						if (c.equals(String.class)) {
							result.put(key, (T) String.valueOf(p.get(key)));
						} else {
							result.put(key, (T) p.get(key));
						}

					}
				}

				return result;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public interface Service_cREST_1 {
		@javax.ws.rs.Path("/")
		@javax.ws.rs.GET()
		@javax.ws.rs.Produces({ "application/xml", "text/xml", "application/json" })
		Object getAllCustomers();

		@javax.ws.rs.Path("/{id}")
		@javax.ws.rs.GET()
		@javax.ws.rs.Produces({ "application/xml", "text/xml", "application/json" })
		Object getCustomer(@javax.ws.rs.PathParam("id") String id);

		@javax.ws.rs.Path("/")
		@javax.ws.rs.POST()
		@javax.ws.rs.Consumes({ "application/xml", "text/xml", "application/json" })
		@javax.ws.rs.Produces({ "*/*" })
		Object addCustomer(beans.Customer payload);

		@javax.ws.rs.Path("/{id}")
		@javax.ws.rs.PUT()
		@javax.ws.rs.Consumes({ "application/xml", "text/xml", "application/json" })
		@javax.ws.rs.Produces({ "*/*" })
		Object updateCustomer(@javax.ws.rs.PathParam("id") String id, beans.Customer payload);

		@javax.ws.rs.Path("/{id}")
		@javax.ws.rs.DELETE()
		@javax.ws.rs.Produces({ "*/*" })
		Object deleteCustomer(@javax.ws.rs.PathParam("id") String id);
	}

	public String getCXFRSEndpointAddress(String endpointUrl) {

		if (inOSGi || inMS) {

			if (endpointUrl != null && !endpointUrl.trim().isEmpty() && !endpointUrl.contains("://")) {

				if (endpointUrl.startsWith("/services")) {
					endpointUrl = endpointUrl.substring("/services".length());
				}

				if (!endpointUrl.startsWith("/")) {
					endpointUrl = '/' + endpointUrl;
				}
			}
			return endpointUrl;
		}

		String defaultEndpointUrl = "http://127.0.0.1:8090/";

		if (null == endpointUrl || endpointUrl.trim().isEmpty()) {

			endpointUrl = defaultEndpointUrl;

		} else if (!endpointUrl.contains("://")) { // relative

			if (endpointUrl.startsWith("/")) {
				endpointUrl = endpointUrl.substring(1);
			}

			endpointUrl = defaultEndpointUrl + endpointUrl;
		}

		return endpointUrl;
	}

	static class CxfPayloadProvider implements
			javax.ws.rs.ext.MessageBodyWriter<org.apache.camel.component.cxf.CxfPayload<javax.xml.transform.Source>> {
		public boolean isWriteable(Class<?> cls, java.lang.reflect.Type type, java.lang.annotation.Annotation[] anns,
				javax.ws.rs.core.MediaType mt) {
			return cls == org.apache.camel.component.cxf.CxfPayload.class;
		}

		public long getSize(org.apache.camel.component.cxf.CxfPayload<javax.xml.transform.Source> obj, Class<?> cls,
				java.lang.reflect.Type type, java.lang.annotation.Annotation[] anns, javax.ws.rs.core.MediaType mt) {
			return -1;
		}

		public void writeTo(org.apache.camel.component.cxf.CxfPayload<javax.xml.transform.Source> obj, Class<?> cls,
				java.lang.reflect.Type type, java.lang.annotation.Annotation[] anns, javax.ws.rs.core.MediaType mt,
				javax.ws.rs.core.MultivaluedMap<String, Object> headers, java.io.OutputStream os)
				throws java.io.IOException, javax.ws.rs.WebApplicationException {
			java.util.List<javax.xml.transform.Source> bodySource = obj.getBodySources();
			if (bodySource == null || bodySource.size() != 1) {
				throw new javax.ws.rs.InternalServerErrorException();
			}
			try {
				org.apache.cxf.staxutils.StaxUtils.copy(bodySource.get(0), os);
			} catch (javax.xml.stream.XMLStreamException ex) {
				throw new javax.ws.rs.InternalServerErrorException(ex);
			}

		}
	}

	private String propertyToString(Object obj) {
		if (obj != null && obj instanceof java.util.Date) {
			return String.format("yyyy-MM-dd HH:mm:ss;%tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", obj);
		} else {
			return String.valueOf(obj);
		}
	}

	@Override
	public void configure() throws java.lang.Exception {
		doConfig();
	}

	public void destroy() {

	}

	public void doConfig() throws java.lang.Exception {
		final /* org.apache.camel.model.Model */CamelContext camelContext = getContext();

		final org.apache.camel.impl.SimpleRegistry registry = new org.apache.camel.impl.SimpleRegistry();
		final org.apache.camel.impl.CompositeRegistry compositeRegistry = new org.apache.camel.impl.CompositeRegistry();
		compositeRegistry.addRegistry(camelContext.getRegistry());
		compositeRegistry.addRegistry(registry);
		((org.apache.camel.impl.DefaultCamelContext) camelContext).setRegistry(compositeRegistry);

		java.util.List<Object> providers = new java.util.ArrayList<Object>();
		providers.add(new com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider());
		providers.add(new com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider());
		registry.put("providers", providers);

		if (inOSGi) {

		}

		// read context values
		readContextValues(contextStr);

		org.apache.cxf.jaxrs.AbstractJAXRSFactoryBean factory_cREST_1;
		org.apache.cxf.jaxrs.JAXRSServerFactoryBean sf_cREST_1 = new org.apache.cxf.jaxrs.JAXRSServerFactoryBean();
		factory_cREST_1 = sf_cREST_1;
		sf_cREST_1.setServiceClass(Service_cREST_1.class);
		sf_cREST_1.setResourceProvider(Service_cREST_1.class,
				new org.apache.camel.component.cxf.jaxrs.CamelResourceProvider(Service_cREST_1.class));
		sf_cREST_1.setProvider(new CxfPayloadProvider());
		// avoid JAXBException in runtime
		sf_cREST_1.setProvider(new org.apache.cxf.jaxrs.provider.SourceProvider());

		factory_cREST_1.setAddress(getCXFRSEndpointAddress("/services/customers"));
		registry.put("cREST_1", factory_cREST_1);
		java.util.List<Object> providers_cREST_1 = (java.util.List<Object>) registry.get("providers");
		if (providers_cREST_1 == null) {
			providers_cREST_1 = new java.util.ArrayList<Object>();
		}
		providers_cREST_1.addAll(sf_cREST_1.getProviders());
		registry.put("providers", providers_cREST_1);
		registry.put("features_cREST_1", sf_cREST_1.getFeatures() != null ? sf_cREST_1.getFeatures()
				: new java.util.ArrayList<org.apache.cxf.feature.Feature>());
		registry.put("inInterceptors_cREST_1", sf_cREST_1.getInInterceptors() != null ? sf_cREST_1.getInInterceptors()
				: new java.util.ArrayList<org.apache.cxf.interceptor.Interceptor<? extends org.apache.cxf.message.Message>>());
		registry.put("outInterceptors_cREST_1", sf_cREST_1.getOutInterceptors() != null
				? sf_cREST_1.getOutInterceptors()
				: new java.util.ArrayList<org.apache.cxf.interceptor.Interceptor<? extends org.apache.cxf.message.Message>>());
		registry.put("properties_cREST_1", sf_cREST_1.getProperties() != null ? sf_cREST_1.getProperties()
				: new java.util.HashMap<String, Object>());
		registry.put("endpointConfigurer_cREST_1", new org.apache.camel.component.cxf.jaxrs.CxfRsEndpointConfigurer() {
			@Override
			public void configure(org.apache.cxf.jaxrs.AbstractJAXRSFactoryBean factoryBean) {
			}

			@Override
			public void configureClient(org.apache.cxf.jaxrs.client.Client client) {
			}

			@Override
			public void configureServer(org.apache.cxf.endpoint.Server server) {

			}
		});
		{
			Object beanInstance = null;
			beans.Customers customers = new beans.Customers();

			beans.Customer customer = new beans.Customer();
			customer.setFirstName(TalendDataGenerator.getFirstName());
			customer.setLastName(TalendDataGenerator.getLastName());
			customer.setCity(TalendDataGenerator.getUsCity());
			customers.addCustomer(customer);

			customer = new beans.Customer();
			customer.setFirstName(TalendDataGenerator.getFirstName());
			customer.setLastName(TalendDataGenerator.getLastName());
			customer.setCity(TalendDataGenerator.getUsCity());
			customers.addCustomer(customer);

			customer = new beans.Customer();
			customer.setFirstName(TalendDataGenerator.getFirstName());
			customer.setLastName(TalendDataGenerator.getLastName());
			customer.setCity(TalendDataGenerator.getUsCity());
			customers.addCustomer(customer);

			beanInstance = customers;

			if (beanInstance != null) {
				registry.put("customers", beanInstance);
			}
		}

		from("direct:getAllCustomers").routeId("RestAPI_Demo_Cloudwatch_cDirect_1_getAllCustomers").setBody()
				.simple("ref:customers").id("RestAPI_Demo_Cloudwatch_cSetBody_1_getAllCustomers")
				.to("log:cxfrs_provider.cLog_1" + "?level=WARN")

				.id("RestAPI_Demo_Cloudwatch_cLog_1");
		from("direct:addCustomer").routeId("RestAPI_Demo_Cloudwatch_cDirect_2_addCustomer").setBody()
				.simple("${bean:customers.addCustomer}").id("RestAPI_Demo_Cloudwatch_cSetBody_2_addCustomer")
				.to("log:DemoRESTRoute.cLog_3" + "?level=WARN")

				.id("RestAPI_Demo_Cloudwatch_cLog_2");
		from("direct:getCustomer").routeId("RestAPI_Demo_Cloudwatch_cDirect_3_getCustomer").setBody()
				.simple("${bean:customers.getCustomer}").id("RestAPI_Demo_Cloudwatch_cSetBody_3_getCustomer")
				.to("log:cxfrs_provider.cLog_2" + "?level=WARN")

				.id("RestAPI_Demo_Cloudwatch_cLog_3");
		from("direct:updateCustomer").routeId("RestAPI_Demo_Cloudwatch_cDirect_4_updateCustomer").setBody()
				.simple("${bean:customers.updateCustomer(body[0], body[1])}")
				.id("RestAPI_Demo_Cloudwatch_cSetBody_4_updateCustomer").to("log:DemoRESTRoute.cLog_4" + "?level=WARN")

				.id("RestAPI_Demo_Cloudwatch_cLog_4");

		from(inOSGi || inMS ? "cxfrs://" + getCXFRSEndpointAddress("/customers")
				+ "?resourceClasses=local_project.restapi_demo_cloudwatch_0_1.RestAPI_Demo_Cloudwatch$Service_cREST_1"
				+ "&features=#features_cREST_1" + "&inInterceptors=#inInterceptors_cREST_1"
				+ "&outInterceptors=#outInterceptors_cREST_1" + "&properties=#properties_cREST_1"
				+ "&cxfRsEndpointConfigurer=#endpointConfigurer_cREST_1" + "&providers=#providers"
				+ "&loggingFeatureEnabled=true"
				: "cxfrs://" + getCXFRSEndpointAddress("http://127.0.0.1:8090" + "/services/customers")
						+ "?resourceClasses=local_project.restapi_demo_cloudwatch_0_1.RestAPI_Demo_Cloudwatch$Service_cREST_1"
						+ "&features=#features_cREST_1" + "&inInterceptors=#inInterceptors_cREST_1"
						+ "&outInterceptors=#outInterceptors_cREST_1" + "&properties=#properties_cREST_1"
						+ "&cxfRsEndpointConfigurer=#endpointConfigurer_cREST_1" + "&providers=#providers"
						+ "&loggingFeatureEnabled=true").process(new org.apache.camel.Processor() {
							public void process(org.apache.camel.Exchange exchange) throws Exception {
								org.apache.camel.Message inMessage = exchange.getIn();
								inMessage.setHeader("http_query",
										org.apache.cxf.jaxrs.utils.JAXRSUtils.getStructuredParams(
												(String) inMessage.getHeader(org.apache.camel.Exchange.HTTP_QUERY), "&",
												false, false));
							}
						}).routeId("RestAPI_Demo_Cloudwatch_cREST_1_DemoREST")

								.recipientList().simple("direct:${header.operationName}")
								.id("RestAPI_Demo_Cloudwatch_cRecipientList_1");
	}

	private org.apache.camel.main.Main main;

	private void run() throws java.lang.Exception {
		main = new org.apache.camel.main.Main() {

			protected CamelContext createContext() {
				final org.apache.camel.impl.DefaultCamelContext camelContext = new org.apache.camel.spring.SpringCamelContext(
						new org.springframework.context.support.ClassPathXmlApplicationContext(
								"META-INF/spring/restapi_demo_cloudwatch.xml"));
				camelContext.setName("RestAPI_Demo_Cloudwatch");
				// add notifier
				java.util.Collection<org.apache.camel.management.JmxNotificationEventNotifier> jmxEventNotifiers = camelContext
						.getRegistry().findByType(org.apache.camel.management.JmxNotificationEventNotifier.class);
				if (jmxEventNotifiers != null && !jmxEventNotifiers.isEmpty()) {
					camelContext.getManagementStrategy().addEventNotifier(jmxEventNotifiers.iterator().next());
				}
				// add statistics which shows on the connection
				final routines.system.RunStat runStat = new routines.system.RunStat();
				runStat.openSocket(true);
				runStat.setAllPID(pid, pid, pid, "RestAPI_Demo_Cloudwatch");
				try {
					runStat.startThreadStat(clientHost, portStats);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
				runStat.updateStatOnJob(routines.system.RunStat.JOBSTART, null);

				final Map<String, String> targetNodeToConnectionMap = new HashMap<String, String>();
				targetNodeToConnectionMap.put("RestAPI_Demo_Cloudwatch_cSetBody_1_getAllCustomers", "route2");
				targetNodeToConnectionMap.put("RestAPI_Demo_Cloudwatch_cLog_1", "route3");
				targetNodeToConnectionMap.put("RestAPI_Demo_Cloudwatch_cSetBody_2_addCustomer", "route6");
				targetNodeToConnectionMap.put("RestAPI_Demo_Cloudwatch_cLog_2", "route7");
				targetNodeToConnectionMap.put("RestAPI_Demo_Cloudwatch_cSetBody_3_getCustomer", "route4");
				targetNodeToConnectionMap.put("RestAPI_Demo_Cloudwatch_cLog_3", "route5");
				targetNodeToConnectionMap.put("RestAPI_Demo_Cloudwatch_cSetBody_4_updateCustomer", "route8");
				targetNodeToConnectionMap.put("RestAPI_Demo_Cloudwatch_cLog_4", "route9");
				targetNodeToConnectionMap.put("RestAPI_Demo_Cloudwatch_cRecipientList_1", "route1");
				for (String connection : targetNodeToConnectionMap.values()) {
					runStat.updateStatOnConnection(connection, routines.system.RunStat.BEGIN, 0);
				}
				camelContext.addInterceptStrategy(new org.apache.camel.spi.InterceptStrategy() {
					public org.apache.camel.Processor wrapProcessorInInterceptors(CamelContext context,
							final org.apache.camel.model.ProcessorDefinition<?> node,
							final org.apache.camel.Processor target, org.apache.camel.Processor nextTarget)
							throws Exception {
						return new org.apache.camel.processor.DelegateAsyncProcessor(target) {
							public boolean process(org.apache.camel.Exchange exchange,
									org.apache.camel.AsyncCallback callback) {
								final String connection = targetNodeToConnectionMap.get(node.getId());
								if (null != connection) {
									runStat.updateStatOnConnection(targetNodeToConnectionMap.get(node.getId()),
											routines.system.RunStat.RUNNING, 1);
								}
								return super.process(exchange, callback);
							}
						};
					}
				});
				return camelContext;
			}
		};

		// add route
		main.addRouteBuilder(this);

		main.run();
	}

	public void stop() throws java.lang.Exception {
		if (main != null) {
			main.stop();
		}
	}

	public void shutdown() throws java.lang.Exception {
		if (main != null) {
			main.shutdown();
		}
	}

	private final ContextProperties context = new ContextProperties();

	public static class ContextProperties extends Properties {

		public String getProperty(String key) {

			String rs = null;

			if (arguments != null) {
				try {

					if (arguments instanceof java.util.HashMap) {
						rs = (String) ((java.util.HashMap) arguments).get(key);
					} else {

						java.lang.reflect.Method getProperty = arguments.getClass().getDeclaredMethod("getProperty",
								new Class[] { String.class });
						getProperty.setAccessible(true);

						rs = (String) getProperty.invoke(arguments, key);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (rs == null) {
				rs = super.getProperty(key);
			}

			return rs;
		}

		public void synchronizeContext() {
		}

	}

	private static String contextStr = "Default";

	public void setContextName(String contextName) {
		contextStr = contextName;
	}

	private int portStats = -1;
	private String clientHost = "localhost";
	private String pid;

	private final Properties context_param = new Properties();

	public static void main(String[] args) {
		int exitCode = new RestAPI_Demo_Cloudwatch().runJobInTOS(args);
		if (exitCode != 0) {
			System.exit(exitCode);
		}
	}

	@Override
	public String[][] runJob(String[] args) {
		int exitCode = runJobInTOS(args);
		return new String[][] { { Integer.toString(exitCode) } };
	}

	@Override
	public int runJobInTOS(String[] args) {
		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}

		if (pid == null) {
			pid = TalendString.getAsciiRandomString(6);
		}
		try {
			run();
		} catch (java.lang.Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	private boolean contextValuesRead = false;

	/**
	 * read context values from specified context
	 * 
	 * @parameter contextName : the name of context while will be used
	 */
	private void readContextValues(String contextName) {
		if (contextValuesRead) {
			return;
		}
		contextValuesRead = true;
		try {
			java.io.InputStream inContext = RestAPI_Demo_Cloudwatch.class.getClassLoader().getResourceAsStream(
					"local_project/restapi_demo_cloudwatch_0_1/contexts/" + contextName + ".properties");

			if (inContext != null) {
				try {
					context.load(inContext);
					if (null != getContext().hasComponent("RestAPI_Demo_Cloudwatch_properties")) {
						org.apache.camel.component.properties.PropertiesComponent props = getContext().getComponent(
								"RestAPI_Demo_Cloudwatch_properties",
								org.apache.camel.component.properties.PropertiesComponent.class);
						context.putAll(props.getOverrideProperties());
					}

				} finally {
					inContext.close();
				}
			} else {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextName);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
			}
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextName);
			ie.printStackTrace();
		}
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
			}
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		}
	}
}
