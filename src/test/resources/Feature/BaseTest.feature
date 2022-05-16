Feature: Example

  Background:
    Given I set UserEmail value in Data Scenario

  @test
  Scenario: Get Sites
    Given I am in App main site



  @test
  Scenario: Get URL and close windows
    Given I go to site http:\\www.google.com.ar
    Then I close the windows

  @test
  Scenario: Get URL and quit the aplication
    Given I go to site http:\\www.google.com.ar
    Then I quit the aplication

  @test
  Scenario: Load the DOM
    Given I go to site http:\\www.google.com.ar
    Then I load the DOM Information galicia.json

  @test
  Scenario: I do a click
    Given I go to site https://www.spotify.com/us/signup
    Then I load the DOM Information spotify_registro.json
    And I do a click in the element Email
    And I set Email with the text gn.rosina@gmail.com

  @test
  Scenario: I save text Titulo
    #Given I go to site https://www.spotify.com/us/signup
    Given I am in App main site
    Then I load the DOM Information spotify_registro.json
    And Save text of Titulo as Scenario Content
    And I set Email with key value Titulo.text

  @test
  Scenario: I complete the form
    Given I go to site https://www.spotify.com/us/signup
    Then I load the DOM Information spotify_registro.json
    And I do a click in the element Email
    And I set Email with the text gn.rosina@gmail.com
    And I do a click in the element ConfirmaEmail
    And I set ConfirmaEmail with the text gn.rosina@gmail.com
    And I wait for element Pass to be visible
    And I do a click in the element Pass
    And I set Pass with the text Benja12345
    And I do a click in the element Llamemos
    And I set Llamemos with the text gabriela
    And I wait for element Mes de Nacimiento to be present
    And I set text Febrero in dropdown Mes de Nacimiento
    And I set index 3 in dropdown Mes de Nacimiento

  @test
  Scenario: I validate message 1
    Given I am in App main site
    Then I load the DOM Information spotify_registro.json
    And I do a click in the element Email
    And I set Email with the text pepivatto@gmail.com
    And I check if Email Error error message is false

  @test
  Scenario: I validate message 2
    Given I am in App main site
    Then I load the DOM Information spotify_registro.json
    And I do a click in the element Email
    And I set Email with the text pepivatto@gmail.com
    And I do a click in the element ConfirmaEmail
    And I check if Email Error error message is false

  @test
  Scenario: I validate message 3
    Given I am in App main site
    Then I load the DOM Information spotify_registro.json
    And I do a click in the element Email
    And I set Email with the text pepivatto@gmail.com
    And I do a click in the element ConfirmaEmail
    And I check if Email Error error message is true

  @test
  Scenario: Frames
    Given I go to site https://chercher.tech/practice/frames-example-selenium-webdriver
    And I load the DOM Information frames.json
    And I switch to Frame: Frame2
    And I set text Avatar in dropdown Frame2 Select
    And I switch to parent frame
    And I switch to Frame: Frame1
    And set element Frame1 input with text Esto es una prueba
    And I switch to Frame: Frame3


  Scenario: I complete the form with checkboxes
    Given I go to site https://www.spotify.com/us/signup
    Then I load the DOM Information spotify_registro.json
    And I do a click in the element Email
    And I set Email with the text gn.rosina@gmail.com
    And I do a click in the element ConfirmaEmail
    And I set ConfirmaEmail with the text gn.rosina@gmail.com
    And I check the checkbox having male
    And I check the checkbox having female
    And I check the checkbox having no binario
    And I check the checkbox having Compartir Datos

  @test
  Scenario: Amazon with java script click
    Given I go to site https://www.amazon.es/
    And I load the DOM Information amazon.json
    And I do a click with JS in the element Mi Cuenta
    And I wait for element Mis Pedidos to be present

  @test
  Scenario: Amazon with java script scroll sin usar funcion scroll
    Given I go to site https://www.amazon.es/
    And I load the DOM Information amazon.json
    And I do a click in the element Aceptar Cookies
    And I do a click in the element Sobre Amazon

  @test
  Scenario: Amazon with java script scroll usando funcion scroll 1
    Given I go to site https://www.amazon.es/
    And I load the DOM Information amazon.json
    And I do a click in the element Aceptar Cookies
    And I scroll to element Sobre Amazon
    And I scroll to top of page
    And I wait for element Sobre Amazon to be present


  @test
  Scenario: Amazon with java script scroll usando funcion scroll 2
    Given I go to site https://www.amazon.es/
    And I load the DOM Information amazon.json
    And I do a click in the element Aceptar Cookies
    And I scroll to end of page
    And I do a click in the element Sobre Amazon
    And I scroll to end of page
    And I scroll to top of page

  @test
  Scenario: Open New Tab
    Given I go to site https://www.amazon.es/
    And I open new tab with  URL https://chercher.tech/practice/frames-example-selenium-webdriver
    #Me posiciono sobre la ultima pagina que abrio
    And I go to Practice window
    Then I load the DOM Information frames.json
    And I switch to Frame: Frame2
    And I set text Avatar in dropdown Frame2 Select
    When I go to Principal window
    And I open new tab with  URL https://www.google.com
    And I go to Google window
    And I go to Practice window
    And I go to Principal window
    And I load the DOM Information amazon.json
    And I scroll to element Sobre Amazon
    And I do a click with JS in the element Mi Cuenta
    
  @test
  Scenario: Handle Alerts
    Given I go to site  https://www.w3schools.com/jsref/tryit.asp?filename=tryjsref_alert
    Then I load the DOM Information frames.json
    And I switch to Frame: Frame4 Alerta
    And I click in element Alert
    And I wait 5 seconds
    Then I accept alert

  @test
  Scenario: Take a ScreenShot
    Given I go to site https://www.spotify.com/us/signup
   # And I wait site is loaded
    And I take screenshot: spotify


  @test
  Scenario: I complete the form with assert
    Given I go to site https://www.spotify.com/us/signup
    Then I load the DOM Information spotify_registro.json
    And I do a click in the element Email
    And I set Email with the text pepivatto@gmail.com
    And I do a click in the element ConfirmaEmail
    Then Assert if Email Existente contains text Este correo electrónico ya está conectado a una cuenta.
    Then Assert if Email Existente is equals text Este correo electrónico ya está conectado a una cuenta. Inicia sesión.

  @test
  Scenario: I validate if a lement is present
    Given I go to site https://www.spotify.com/us/signup
    Then I load the DOM Information spotify_registro.json
    And I do a click in the element Email
    And I set Email with the text pepivatto@gmail.com
    And I do a click in the element ConfirmaEmail
    Then check if element Registrate con Google is Displayed


  @test
  Scenario: Take a ScreenShot with allure
    Given I go to site https://www.spotify.com/us/signup
    And I wait site is loaded
    And I take screenshot: spotify
    And I Attach a Screenshot to Report: Para Allure

