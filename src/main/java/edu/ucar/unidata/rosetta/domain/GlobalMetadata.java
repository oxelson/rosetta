/*
 * Copyright (c) 2012-2020 University Corporation for Atmospheric Research/Unidata.
 * See LICENSE for license information.
 */

package edu.ucar.unidata.rosetta.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Form-backing object for the wizard to collect a global metadata. Lombok automatic generation of noop constructor,
 * getters, setters, equals, hashCode and toString.
 *
 * @author oxelson
 */
@Data
@NoArgsConstructor
public class GlobalMetadata {
  private String metadataGroup;
  private String metadataKey;
  private String metadataValue;
  private String metadataValueType; // STRING, INTEGER, etc.
  private String wizardDataId;
}
