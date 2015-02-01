/**
 * 
 */
package de.wollistik.dlna.server;

import org.fourthline.cling.binding.annotations.UpnpService;
import org.fourthline.cling.binding.annotations.UpnpServiceId;
import org.fourthline.cling.binding.annotations.UpnpServiceType;

/**
 * @author Wolfgang Wedelich-John<wolfgang.wedelich@wollistik.de>
 *
 */
@UpnpService(serviceId = @UpnpServiceId("wollo-dlna"), serviceType = @UpnpServiceType(value = "dlna", version = 1))
public class DlnaServer {

}
