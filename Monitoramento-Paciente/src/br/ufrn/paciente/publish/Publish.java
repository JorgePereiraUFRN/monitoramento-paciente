/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufrn.paciente.publish;

import br.ufrn.paciente.exceptions.ComunicationException;
import br.ufrn.paciente.exceptions.ConversionErrorException;
import br.ufrn.paciente.exceptions.TopicAlreadyExistsException;

import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

/**
 * 
 * @author Jorge
 */
public class Publish {

	private Client client = Client.create();
	private String topic;
	private String uriHub;

	/*classe responsavel por solicitar a cria��o de topicos ao hub 
	e por enviar as atualiza��es para o hub no seu contrutor recebe
	o identificador do topico e o endere�o do hub*/
	public Publish(String topic, String uriHub) {
		this.topic = topic;
		this.uriHub = uriHub;

	}

	public String getTopic() {
		return topic;
	}

	/*m�todo que solicita ao hub a cria��o de um topico, o topico que ser� 
	criado possue como identificador o identificador do topico recebido
	no contrutor. Este m�todo pode levantar duas exce��es TopicAlreadyExistsException, caso o
	topico que se deseja criar ja exixta, e ComunicationException, caso ocorra problemas
	na comunica��o estre o publish e o hub*/
	public void createTopic() throws ComunicationException,
			TopicAlreadyExistsException {
		String response = " ";
		try {
			WebResource resource = client.resource(uriHub + "register");
			response = resource.put(String.class, topic);
		} catch (Exception e) {
			throw new ComunicationException(e.getMessage());
		}
		if (response.equalsIgnoreCase("the topic " + topic + " already exists")) {
			throw new TopicAlreadyExistsException(response);
		}
	}

	/*m�todo utilizado para publicar atualiza��es no hub, recebe como parametro uma string que ser�
	enviada para o hub, pode levantar a exce��o ComunicationException, caso ocorra problemas
	na comunica��o estre o publish e o hub*/
	
	public void publish(String message) throws ComunicationException {
		try {
			WebResource resource = client.resource(uriHub + "publish/" + topic);
			resource.put(message);
		} catch (Exception e) {
			throw new ComunicationException(e.getMessage());
		}
	}

	/*m�todo utilizado para publicar atualiza��es no hub, recebe como parametro um objeto que ser�
	 convertido para a representa��o Json e posteriormente enviad para o hub , pode levantar a exce��o ComunicationException, caso ocorra problemas
	na comunica��o estre o publish e o hub*/
	public void publish(Object o) throws ConversionErrorException {
		try {
			WebResource resource = client.resource(uriHub + "publish/" + topic);
			String json = new Gson().toJson(o);
			resource.put(json);
		} catch (Exception e) {
			throw new ConversionErrorException("Error converting object +\n"
					+ e.getMessage());
		}
	}

}
