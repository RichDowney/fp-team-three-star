# fp-team-three-star
Team Members: Richard Downey, Eric LaRocco and Daman Knosp

<h3>Project Summary</h3>
  <p>An application that allows Twitter users to post generated content to their Twitter account on a set interval or on
    a scheduled time. 
  </p>

<h3>What You Need</h3>
  <ul>
    <li>A Twitter Account</li>
    <li>A Twiiter API Key</li>
    <li>A Twitter API Secret</li>
  </ul>
  
<h3>Getting Started</h3>
  <ol>
    <li>
      Signing Up For Twitter Account 
      <ul>
        <li>
          To sign up for a Twitter account go to <a href="https://twitter.com/signup" target="blank"> Twitter Sign Up </a> 
        </li> 
        <li> 
          Either sign up with a phone number or link one after the signup process by following 
            <a href="https://support.twitter.com/articles/110250" target="blank"> Twitter Phone TUT </a> 
        </li>
      </ul>
    </li>
    <li>
      Registering An Application 
      <ul>
        <li>
          Register an app by visiting <a href="https://apps.twitter.com/" target="blank"> Manage Twitter Apps </a>
          and clicking "Create New App"
        </li>
        <li>
          Once you have created the new app, click the "Permissions" tab and make sure that "Read and Write" is selected
        </li>
        <li>
          Now click the "Keys and Access Tokens" tab, the "Consumer Key (API Key)" and "Consumer Secret (API Secret)" values
           are need to run the application so save them somewhere or bookmark the page.
        </li>
      </ul>
    </li>
  </ol>
  
  <h3>Using The Application</h3>
  <ol>
  <li>Click "Get Saved API Values" or Take the api values from your registered application's management page and paste them into
    the fields. You can click "Save API Values" to save them for quick future use
  </li>
  <li>Click "Next"</li>
  <li>Click "Get Authorization URL" and copy paste the returned value into a web browser. Note that this is giving the app 
    permission to access your Twitter account so make sure you are signed into the account you want to post to.
  </li>
  <li>
    Click "Authorize App" on the page in your browser and copy paste the displayed code into the "Token Verifier Code" field.
  </li>
  <li>
    Type what you want to tweet into the "Tweet Text Content" field and click "Post Tweet" to post to your Twitter account.
    Ensure that your tweet content is not longer than 140 characters (will be automated in future versions).
  </li>
  <li>
    To post another tweet you must repeat steps 3-5
  </li>
  </ol>
