/*
 * Copyright (c) 2012-2020 University Corporation for Atmospheric Research/Unidata.
 * See LICENSE for license information.
 */

package edu.ucar.unidata.rosetta.domain;

import java.util.Objects;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Object representing a metadata profile schema as outlined in
 * https://github.com/Unidata/rosetta/wiki/Metadata-Profile-Schema
 *
 * Lombok automatic generation of noop constructor, getters, setters, equals, hashCode and toString.
 *
 * @author oxelson
 */
@Data
@NoArgsConstructor
public class MetadataProfile {

  private static final Logger logger = LogManager.getLogger();

  private String attributeName; // required
  private String complianceLevel = "additional";
  private String description;
  private String displayName;
  private String exampleValues;
  private String id;
  private String metadataGroup = "root";
  private String metadataProfileName; // required
  private String metadataProfileVersion; // required
  private String metadataType = "Global";
  private String metadataTypeStructureName = "Global Attributes";
  private String metadataValueType = "String";

}
