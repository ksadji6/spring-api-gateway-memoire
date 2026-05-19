package com.esmt.gateway.filter;

import org.springframework.cloud.gateway.filter.factory.AbstractChangeRequestUriGatewayFilterFactory;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFilter extends AbstractChangeInventoryFilterFactory<AuthenticationFilter.Config>{

}
