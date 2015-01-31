/**
 * 
 */
package de.wollistik.dlna;

import org.fourthline.cling.binding.annotations.AnnotationLocalServiceBinder;
import org.fourthline.cling.model.DefaultServiceManager;
import org.fourthline.cling.model.ValidationException;
import org.fourthline.cling.model.meta.DeviceDetails;
import org.fourthline.cling.model.meta.DeviceIdentity;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.LocalService;
import org.fourthline.cling.model.meta.ManufacturerDetails;
import org.fourthline.cling.model.meta.ModelDetails;
import org.fourthline.cling.model.types.DLNACaps;
import org.fourthline.cling.model.types.DLNADoc;
import org.fourthline.cling.model.types.DLNADoc.Version;
import org.fourthline.cling.model.types.DeviceType;
import org.fourthline.cling.model.types.UDADeviceType;
import org.fourthline.cling.model.types.UDN;

/**
 * @author Wolfgang Wedelich-John<wolfgang.wedelich@wollistik.de>
 *
 */
public class DlnaDeviceFactory {
    public static LocalDevice createDevice() {
        DeviceIdentity identity = new DeviceIdentity(UDN.uniqueSystemIdentifier("DLNA SERVER"));

        DeviceType type = new UDADeviceType("MediaServer",
                1);

        DeviceDetails details = new DeviceDetails("Friendly Binary Light",
                new ManufacturerDetails("ACME"),
                new ModelDetails("Wollo DLNA",
                        "A DLNA Server for your pleasure.",
                        "v1"),
                new DLNADoc[] { new DLNADoc("DMS",
                        Version.V1_5) },
                new DLNACaps(new String[] { "Video" }));

        LocalService<DlnaServer> dlnaServerService = new AnnotationLocalServiceBinder().read(DlnaServer.class);

        dlnaServerService.setManager(new DefaultServiceManager(dlnaServerService,
                DlnaServer.class));

        try {
            return new LocalDevice(identity,
                    type,
                    details,
                    dlnaServerService);
        } catch (ValidationException e) {
            throw new RuntimeException("Could not create LocalDevice",
                    e);
        }
    }
}
