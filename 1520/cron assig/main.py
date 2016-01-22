import logging

import webapp2

from google.appengine.api import users
from google.appengine.api import mail
from google.appengine.ext import ndb
from google.appengine.ext.webapp import template

logging.info("hey this is a message")

class MainPageHandler(webapp2.RequestHandler):
  global globfrom
  global globrec
  globfrom = 'contact@cronassig.appspotmail.com'
  globrec = 'rudy@mt2014.com'
  def get(self):
    mail.send_mail(globfrom, globrec, 'test', 'Cron test2')
    self.response.out.write('''

<html>
  <body style="font-family: Tahoma;">
    <form method="post" action="/">
      Turn Email on or off:</br>
      <input type="radio" name="on" value="on">ON<br>
      <input type="radio" name="on" value="off">OFF<br>
      <input type="submit">
    </form>
  </body>
</html>
    
''')
    
    
  def post(self):
    value1 = self.request.get('on')

    if value1 == 'on':
      global globfrom 
      global globrec
      globfrom = 'contact@cronassig.appspotmail.com'
      globrec = 'trjames@pitt.edu'
      self.response.out.write("You've turned it ON")
      
    else:
      self.response.out.write("You've turned it off")
      global globfrom 
      global globrec
      globfrom = 'contact@cronassig.appspotmail.com'
      globrec = 'rudy@mt2014.com'
    
url_list = [
  ('/', MainPageHandler)
]

# url_list = list()
# url_list.append(('/', MainPageHandler))

app = webapp2.WSGIApplication(url_list)