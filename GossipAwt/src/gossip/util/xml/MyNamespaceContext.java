package gossip.util.xml;

import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;

public class MyNamespaceContext implements NamespaceContext {

	public static final String XML_MY_NAMESPACE = "gs";
	public static final String XML_MY_URI = "uri:gossip";

	@Override
	public String getNamespaceURI(String prefix) {
		if (prefix == null) {
			throw new NullPointerException("Null prefix");
		} else if (XML_MY_NAMESPACE.equals(prefix)) {
			return XML_MY_URI;
		}
		return XMLConstants.XML_NS_URI;
	}

	@Override
	public String getPrefix(String uri) {
		if (XML_MY_URI.equals(uri)) {
			return XML_MY_NAMESPACE;
		}
		throw new UnsupportedOperationException();
	}
	
	

	@Override
	public Iterator<String> getPrefixes(String uri) {
		throw new UnsupportedOperationException();
	}

}
