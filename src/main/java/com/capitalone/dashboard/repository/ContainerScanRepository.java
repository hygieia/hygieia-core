package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.ContainerScan;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository for {@link ContainerScan} data.
 */
public interface ContainerScanRepository extends CrudRepository<ContainerScan, ObjectId> {

    ContainerScan findByIdentifierNameAndIdentifierVersion(String identifierName, String identifierVersion);
    ContainerScan findByIdentifierNameAndIdentifierVersionAndIdentifierUrl(String identifierName, String identifierVersion, String identifierUrl);
    ContainerScan findByIdentifierUrl(String identifierUrl);
    List<ContainerScan> findByBusinessService(String businessService);
    List<ContainerScan> findByBusinessApplication(String businessApplication);
    List<ContainerScan> findByBusinessServiceAndBusinessApplication(String businessService, String businessApplication);
    List<ContainerScan> findByImageId(String imageId);

}
