package model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommandLineArgs {
    String ftlRealm;
    String ftlAppName;
    String ftlEndPoint;
}
