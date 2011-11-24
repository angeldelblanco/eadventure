package es.eucm.eadventure.common.impl.reader.visitors;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.logging.Logger;

import org.w3c.dom.Node;

import es.eucm.eadventure.common.impl.DOMTags;
import es.eucm.eadventure.common.impl.reader.extra.ObjectFactory;
import es.eucm.eadventure.common.model.EAdElement;

/**
 * Visitor for the element. The element should be {@code <element id="ID"
 *  type="ENGINE_TYPE"
 *  class="EDITOR_TYPE"></element>}.
 */
public class ElementNodeVisitor extends NodeVisitor<EAdElement> {
	
	public static final String TAG = "element";

	protected static final Logger logger = Logger.getLogger("ElementNodeVisitor");

	@Override
	public EAdElement visit(Node node, Field field, Object parent) {
		EAdElement element = (EAdElement) ObjectFactory.getObject(node.getTextContent(), EAdElement.class);
		if (element != null) {
			setValue(field, parent, element);
			return element;
		}
		
		Node n = node.getAttributes().getNamedItem(DOMTags.UNIQUE_ID_AT);
		String uniqueId = n != null ? n.getNodeValue() : null;
		n = node.getAttributes().getNamedItem(DOMTags.ID_AT);
		String id = n != null ? n.getNodeValue() : null;

		n = node.getAttributes().getNamedItem(loaderType);
		String clazz = null;
		if (n != null) {
			clazz = node.getAttributes().getNamedItem(loaderType).getNodeValue();
			clazz = translateClass(clazz);
		} else {
			System.out.println("wired null");
		}
		
		Class<?> c = null;
		try {
			c = ClassLoader.getSystemClassLoader().loadClass(clazz);
			Constructor<?> con = c.getConstructor(String.class);
			element = (EAdElement) con.newInstance(new Object[] { id });
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			Constructor<?> con;
			try {
				con = c.getConstructor();
				element = (EAdElement) con.newInstance();
			} catch (NoSuchMethodException e1) {
				logger.info("You must define a constructor without parameters or a constructor only with the id for the class "
						+ c + " in order to make work XML read and write");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {

		}
		if (element != null)
			ObjectFactory.addElement(uniqueId, element);
		setValue(field, parent, element);

		readFields(element, node);
		
		return element;
	}

	@Override
	public String getNodeType() {
		return TAG;
	}

}