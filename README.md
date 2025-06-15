# EventSync - Event Management System

![image](https://github.com/user-attachments/assets/7c3a912e-d12d-41ee-9784-98194c61aba3)

## Overview

EventSync is an event management system that allows organization of events and feedback collection from participants. The application automatically analyzes feedback sentiment using AI-powered sentiment analysis through the Hugging Face API and provides insights into event success.

## Technology Stack

- **Backend:** Java 17+ with Spring Boot
- **Database:** H2 In-Memory Database
- **AI Service:** Hugging Face Inference API (cardiffnlp/twitter-roberta-base-sentiment)
- **Frontend:** HTML, CSS, JavaScript
- **Build Tool:** Maven

## Prerequisites

Before running the application, ensure you have:

1. **Java 17 or higher** installed
   - Check with: `java --version`
   - Download from: https://adoptium.net/

2. **Maven 3.6+** installed
   - Check with: `mvn --version`
   - Download from: https://maven.apache.org/download.cgi

3. **Git** (to clone the repository)

## API Setup - Getting Your Hugging Face Token

To enable sentiment analysis, you'll need a free Hugging Face API token:

### Step 1: Create Hugging Face Account
1. Go to https://huggingface.co/
2. Click "Sign Up" and create a free account
3. Verify your email address

### Step 2: Generate API Token
1. Go to https://huggingface.co/settings/tokens
2. Click "New token"
3. Give it a name (e.g., "EventSync Token")
4. Select "Read" permissions
5. Click "Generate a token"
6. **Copy the token** - you'll need it in the next step

## Installation & Setup

### Step 1: Clone the Repository
```bash
git clone https://github.com/harmyko/event_feedback_analyzer.git
cd event_feedback_analyzer
```

### Step 2: Configure the Application
1. Open `src/main/resources/application.properties`
2. Replace `your_token_goes_here` with your actual Hugging Face API token:
```properties
huggingface.api.token=hf_your_actual_token_here
```

### Step 3: Build the Application
```bash
mvn clean compile
```

### Step 4: Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Usage Guide

### Accessing the Application

1. **Web Interface:** Open http://localhost:8080 in your browser
2. **API Endpoints:** Base URL is http://localhost:8080
3. **H2 Console:** http://localhost:8080/h2-console (for database inspection)
   - JDBC URL: `jdbc:h2:mem:eventsync`
   - Username: `sa`
   - Password: (leave empty)

### Using the Web Interface

The web interface provides a complete user experience:

1. **Create Events:**
   - Fill in the "Create New Event" form
   - Click "Create Event"
   - Note the generated Event ID
     
![image](https://github.com/user-attachments/assets/bf127256-76f6-4529-81e6-8e2ef4c253aa)

2. **View All Events:**
   - Click "Refresh Events" to see all created events
   - Events are displayed with ID, title, description, and creation time
     
![image](https://github.com/user-attachments/assets/708e8293-9a01-44e3-98fe-462915953d84)

3. **Submit Feedback:**
   - Use the Event ID from step 1
   - Enter your username and feedback text
   - Click "Submit Feedback"
   - The system will automatically analyze sentiment
     
![image](https://github.com/user-attachments/assets/d222d05c-9aff-4461-a287-e5efed12d127)

4. **View Event Feedback:**
   - Enter an Event ID
   - Click "Load Feedback" to see all feedback for that event
     
![image](https://github.com/user-attachments/assets/4be6efb2-81d2-4785-8454-0786227f2510)

5. **View Sentiment Summary:**
   - Enter an Event ID
   - Click "Load Summary" to see sentiment breakdown with percentages
     
![image](https://github.com/user-attachments/assets/bd7d86aa-5888-44aa-b18d-8d62231148b1)

### API Endpoints

The application provides the following REST endpoints:

#### Events
- `POST /events` - Create a new event
- `GET /events` - List all events
- `GET /events/{eventId}` - Get specific event details
- `GET /events/{eventId}/summary` - Get sentiment summary for an event

#### Feedback
- `POST /events/{eventId}/feedback` - Submit feedback for an event
- `GET /events/{eventId}/feedback` - Get all feedback for an event

## API Documentation

The application includes **OpenAPI/Swagger documentation** for easy API exploration and testing:

### Accessing Swagger UI
1. Start the application
2. Navigate to: http://localhost:8080/swagger-ui.html
3. Explore all available endpoints with interactive documentation
4. Test API calls directly from the browser interface

## Project Structure

```
eventsync/
├── src/main/java/com/eventsync/
│   ├── controller/              # REST Controllers
│   ├── dto/                     # Data Transfer Objects
│   ├── exception/               # Custom Exceptions & Global Handler
│   ├── model/                   # JPA Entities
│   ├── repository/              # Data Access Layer
│   └── service/                 # Business Logic
├── src/main/resources/
│   ├── static/                  # Web Assets (HTML, CSS, JS)
│   └── application.properties   # Contains the AI API token
└── pom.xml
```

## Troubleshooting

### Common Issues

1. **"Port 8080 already in use"**
   - Kill the process that is using port 8080
   - Or change the port in `application.properties`: `server.port=8081` (or any other free port)

2. **Sentiment Analysis Returns "UNKNOWN"**
   - Check if your Hugging Face API token is correctly configured
   - Verify the token has "Read" permissions
   - Check application logs for API errors

## Future Enhancements

**Potential improvements for deployment:**
- [ ] User authentication and authorization
- [ ] Asynchronous sentiment analysis processing
- [ ] Data persistence (PostgreSQL/MySQL)
- [ ] Cloud deployment
- [ ] API rate limiting
- [ ] Enhanced error handling and logging

# Have fun!
