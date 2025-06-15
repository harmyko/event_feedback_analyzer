const API_BASE = 'http://localhost:8080';

document.getElementById('createEventForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    const title = document.getElementById('eventTitle').value;
    const description = document.getElementById('eventDescription').value;

    try {
        const response = await fetch(`${API_BASE}/events`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                title: title,
                description: description
            })
        });

        if (response.ok) {
            const event = await response.json();
            document.getElementById('createEventMessage').innerHTML =
                `<p class="success">Event created successfully! ID: ${event.id}</p>`;
            document.getElementById('createEventForm').reset();
        } else {
            document.getElementById('createEventMessage').innerHTML =
                `<p class="error">Error creating event</p>`;
        }
    } catch (error) {
        document.getElementById('createEventMessage').innerHTML =
            `<p class="error">Error: ${error.message}</p>`;
    }
});

async function loadEvents() {
    try {
        const response = await fetch(`${API_BASE}/events`);
        const events = await response.json();

        let html = '<h3>Events:</h3>';
        if (events.length === 0) {
            html += '<p>No events found.</p>';
        } else {
            events.forEach(event => {
                html += `
                        <div class="event-item">
                            <strong>ID:</strong> ${event.id}<br>
                            <strong>Title:</strong> ${event.title}<br>
                            <strong>Description:</strong> ${event.description || 'No description'}<br>
                            <strong>Created:</strong> ${event.createdAt}
                        </div>
                    `;
            });
        }
        document.getElementById('eventsList').innerHTML = html;
    } catch (error) {
        document.getElementById('eventsList').innerHTML =
            `<p class="error">Error loading events: ${error.message}</p>`;
    }
}

document.getElementById('feedbackForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    const eventId = document.getElementById('feedbackEventId').value;
    const username = document.getElementById('feedbackUsername').value;
    const content = document.getElementById('feedbackContent').value;

    try {
        const response = await fetch(`${API_BASE}/events/${eventId}/feedback`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                eventId: parseInt(eventId),
                username: username,
                content: content
            })
        });

        if (response.ok) {
            const feedback = await response.json();
            document.getElementById('feedbackMessage').innerHTML =
                `<p class="success">Feedback submitted successfully! Sentiment: ${feedback.sentiment}</p>`;
            document.getElementById('feedbackForm').reset();
        } else {
            let errorMessage = `Error submitting feedback (${response.status})`;
            try {
                const errorData = await response.json();
                if (errorData.message) {
                    errorMessage = errorData.message;
                } else if (errorData.error) {
                    errorMessage = errorData.error;
                }
            } catch (parseError) {
                try {
                    const errorText = await response.text();
                    if (errorText) {
                        errorMessage = errorText;
                    }
                } catch (textError) {
                }
            }

            document.getElementById('feedbackMessage').innerHTML =
                `<p class="error">${errorMessage}</p>`;
        }
    } catch (error) {
        document.getElementById('feedbackMessage').innerHTML =
            `<p class="error">Network error: ${error.message}</p>`;
    }
});

async function loadEventFeedback() {
    const eventId = document.getElementById('viewFeedbackEventId').value;
    if (!eventId) {
        alert('Please enter an Event ID');
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/events/${eventId}/feedback`);
        const feedbacks = await response.json();

        let html = '<h3>Feedback:</h3>';
        if (feedbacks.length === 0) {
            html += '<p>No feedback found for this event.</p>';
        } else {
            feedbacks.forEach(feedback => {
                html += `
                        <div class="feedback-item">
                            <strong>User:</strong> ${feedback.username}<br>
                            <strong>Content:</strong> ${feedback.content}<br>
                            <strong>Sentiment:</strong> ${feedback.sentiment}<br>
                            <strong>Posted:</strong> ${feedback.createdAt}
                        </div>
                    `;
            });
        }
        document.getElementById('eventFeedback').innerHTML = html;
    } catch (error) {
        document.getElementById('eventFeedback').innerHTML =
            `<p class="error">Error loading feedback: ${error.message}</p>`;
    }
}

async function loadEventSummary() {
    const eventId = document.getElementById('summaryEventId').value;
    if (!eventId) {
        alert('Please enter an Event ID');
        return;
    }

    try {
        const response = await fetch(`${API_BASE}/events/${eventId}/summary`);
        if (response.ok) {
            const summary = await response.json();
            document.getElementById('eventSummary').innerHTML = `
                    <h3>Sentiment Summary for Event ${summary.eventId}:</h3>
                    <table>
                        <tr>
                            <th>Metric</th>
                            <th>Count</th>
                            <th>Percentage</th>
                        </tr>
                        <tr>
                            <td>Total Feedback</td>
                            <td>${summary.totalFeedback}</td>
                            <td>100%</td>
                        </tr>
                        <tr>
                            <td>Positive</td>
                            <td>${summary.positiveCount}</td>
                            <td>${summary.positivePercentage}%</td>
                        </tr>
                        <tr>
                            <td>Neutral</td>
                            <td>${summary.neutralCount}</td>
                            <td>${summary.neutralPercentage}%</td>
                        </tr>
                        <tr>
                            <td>Negative</td>
                            <td>${summary.negativeCount}</td>
                            <td>${summary.negativePercentage}%</td>
                        </tr>
                    </table>
                `;
        } else {
            document.getElementById('eventSummary').innerHTML =
                `<p class="error">Event not found</p>`;
        }
    } catch (error) {
        document.getElementById('eventSummary').innerHTML =
            `<p class="error">Error: ${error.message}</p>`;
    }
}

window.addEventListener('load', function() {
    loadEvents();
});