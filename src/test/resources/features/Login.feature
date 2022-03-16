@Login
Feature: Autenticación de usuarios

  Background: Voy al entorno de test
    Given estoy en el entorno de test


  Scenario Outline: Me identifico con mis credenciales para ver mi perfil de usuario
    And voy a la modal de identificación
    When me identifico con el usuario "<user>" y contraseña "<password>"
    Then estoy en la página de mi perfil de usuario

    Examples:
      | user                  | password |
      | qa-customer@domain.es | password |

  Scenario Outline: Me identifico con datos incorrectos para obtener validaciones
    And voy a la modal de identificación
    When me identifico con el usuario "<user>" y contraseña "<password>"
    Then veo el mensaje "<message>" en el elemento "<element>"

    Examples:
      | case            | user                  | password | message                    | element                                           |
      | empty data      |                       |          | An email address required. | #center_column > div.alert.alert-danger > ol > li |
      | empty email     |                       | password | An email address required. | #center_column > div.alert.alert-danger > ol > li |
      | empty password  | qa-customer@domain.es |          | Password is required.      | #center_column > div.alert.alert-danger > ol > li |
      | bad credentials | qa-customer@domain.es | 12345    | Authentication failed.     | #center_column > div.alert.alert-danger > ol > li |



