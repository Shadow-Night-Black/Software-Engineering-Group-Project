package data;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public abstract class XMLWriter {
	private Document doc;
	private Transformer transformer;
	private DOMSource source;
	private StreamResult result;
	public XMLWriter(String fileName) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = dbFactory.newDocumentBuilder();
			doc = docBuilder.newDocument();

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			setTransformer(transformerFactory.newTransformer());
			setSource(new DOMSource(doc));
			setResult(new StreamResult(new File(fileName)));

		} catch (Exception e) {
            e.printStackTrace();

		}
	}
	public Transformer getTransformer() {
		return transformer;
	}
	private void setTransformer(Transformer transformer) {
		this.transformer = transformer;
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
    }
	public DOMSource getSource() {
		return source;
	}
	private void setSource(DOMSource source) {
		this.source = source;
	}
	public StreamResult getResult() {
		return result;
	}
	private void setResult(StreamResult result) {
		this.result = result;
	}
	public Document getDocument(){
		return doc;
	}


}
