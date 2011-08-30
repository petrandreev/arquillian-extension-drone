/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.drone.webdriver.factory;

import java.lang.annotation.Annotation;

import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.drone.spi.Configurator;
import org.jboss.arquillian.drone.spi.Destructor;
import org.jboss.arquillian.drone.spi.Instantiator;
import org.jboss.arquillian.drone.webdriver.configuration.IPhoneDriverConfiguration;
import org.openqa.selenium.iphone.IPhoneDriver;

/**
 * Factory which combines {@link org.jboss.arquillian.drone.spi.Configurator},
 * {@link org.jboss.arquillian.drone.spi.Instantiator} and {@link org.jboss.arquillian.drone.spi.Destructor} for IPhoneDriver.
 *
 * @author <a href="kpiwko@redhat.com>Karel Piwko</a>
 *
 */
public class IPhoneDriverFactory implements Configurator<IPhoneDriver, IPhoneDriverConfiguration>,
        Instantiator<IPhoneDriver, IPhoneDriverConfiguration>, Destructor<IPhoneDriver> {

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.arquillian.drone.spi.Sortable#getPrecedence()
     */
    public int getPrecedence() {
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.arquillian.drone.spi.Destructor#destroyInstance(java.lang.Object)
     */
    public void destroyInstance(IPhoneDriver instance) {
        instance.quit();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.arquillian.drone.spi.Instantiator#createInstance(org.jboss.arquillian.drone.spi.DroneConfiguration)
     */
    public IPhoneDriver createInstance(IPhoneDriverConfiguration configuration) {

        String remoteAddress = configuration.getRemoteAddress();

        // default
        if (Validate.empty(remoteAddress)) {
            return SecurityActions.newInstance(configuration.getImplementationClass(), new Class<?>[0], new Object[0],
                    IPhoneDriver.class);
        }
        // remote address specified
        else {
            Validate.isValidUrl(remoteAddress, "Remote address must be a valid url, " + remoteAddress);
            return SecurityActions.newInstance(configuration.getImplementationClass(), new Class<?>[] { String.class },
                    new Object[] { remoteAddress }, IPhoneDriver.class);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.jboss.arquillian.core.spi.LoadableExtension#createConfiguration(org.jboss.arquillian.impl.configuration.api.
     * ArquillianDescriptor, java.lang.Class)
     */
    public IPhoneDriverConfiguration createConfiguration(ArquillianDescriptor descriptor, Class<? extends Annotation> qualifier) {

        return new IPhoneDriverConfiguration().configure(descriptor, qualifier);
    }

}