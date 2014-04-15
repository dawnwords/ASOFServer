package edu.fudan.se.service.bundle.chooser;

import edu.fudan.se.service.bundle.message.Response;
import edu.fudan.se.service.bundle.message.ServiceDescription;

public interface BundleChooser {
	Response getResponseByDescription(ServiceDescription description);
}
