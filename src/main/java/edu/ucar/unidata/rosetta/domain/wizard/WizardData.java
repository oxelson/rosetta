/*
 * Copyright (c) 2012-2020 University Corporation for Atmospheric Research/Unidata.
 * See LICENSE for license information.
 */

package edu.ucar.unidata.rosetta.domain.wizard;

import lombok.Data;

/**
 * Form-backing object to collect data from the wizard. Lombok automatic generation of getters, setters, equals,
 * hashCode and toString.
 *
 * @author oxelson
 */
@Data
public class WizardData {

  private String cfType;
  private String community;
  private String dataFileType;
  private String delimiter;
  private String globalMetadata;
  private String headerLineNumbers;
  private String id;
  private String metadataProfile;
  private boolean noHeaderLines;
  private String platform;
  private String variableMetadata;

  /**
   * Returns whether there are no leader lines in the file.
   *
   * @return true if no header lines; otherwise false.
   */
  public boolean hasNoHeaderLines() {
    return noHeaderLines;
  }
}
