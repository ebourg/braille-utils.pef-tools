package org.daisy.braille.utils.pef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.daisy.braille.utils.api.factory.FactoryProperties;
import org.daisy.braille.utils.api.validator.Validator;
import org.daisy.braille.utils.api.validator.ValidatorProvider;
import org.osgi.service.component.annotations.Component;

@Component
@Deprecated
public class DefaultValidatorProvider implements ValidatorProvider {
	private static final String PEF_MIME = "application/x-pef+xml"; 
	//Legacy value for PEF Validator value provider
	private static final String PEF_LEGACY = PEFValidator.class.getCanonicalName();

	private static class PEFValidatorProperties implements FactoryProperties {
		private final String identifier;
		private PEFValidatorProperties(String identifier) {
			this.identifier = identifier;
		}

		@Override
		public String getIdentifier() {
			return identifier;
		}
		@Override
		public String getDisplayName() {
			return "PEF Validator";
		}
		@Override
		public String getDescription() {
			return "Validates PEF 1.0 files";
		}
	}

	private final Collection<FactoryProperties> list;

	public DefaultValidatorProvider() {
		ArrayList<FactoryProperties> tmp = new ArrayList<FactoryProperties>();
		tmp.add(new PEFValidatorProperties(PEF_MIME));
		tmp.add(new PEFValidatorProperties(PEF_LEGACY));
		list = Collections.unmodifiableCollection(tmp);
	}

	@Override
	public Collection<FactoryProperties> list() {
		return list;
	}

	@Override
	public Validator newValidator(String identifier) {
		if (PEF_MIME.equals(identifier)) {
			return new PEFValidator(PEF_MIME);
		} else if (PEF_LEGACY.equals(identifier)) {
			return new PEFValidator(PEF_LEGACY);
		} else {
			return null;
		}
	}

}
