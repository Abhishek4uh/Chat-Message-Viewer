package com.abhishek.chatapp.features.home.domain

import com.abhishek.chatapp.features.home.domain.model.FileAttachment
import com.abhishek.chatapp.features.home.domain.model.Message
import com.abhishek.chatapp.features.home.domain.model.MessageType
import com.abhishek.chatapp.features.home.domain.model.Sender
import com.abhishek.chatapp.features.home.domain.model.Thumbnail


object MockData {
    val messages = listOf(
        Message(
            id = "msg-001",
            message = "Hi! I need help booking a flight to Mumbai.",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.USER,
            timestamp = 1703520000000L
        ),
        Message(
            id = "msg-002",
            message = "Hello! I'd be happy to help you book a flight to Mumbai. When are you planning to travel?",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = 1703520030000L
        ),
        Message(
            id = "msg-003",
            message = "Next Friday, December 29th.",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.USER,
            timestamp = 1703520090000L
        ),
        Message(
            id = "msg-004",
            message = "Great! And when would you like to return?",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = 1703520120000L
        ),
        Message(
            id = "msg-005",
            message = "January 5th. Also, I prefer morning flights.",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.USER,
            timestamp = 1703520180000L
        ),
        Message(
            id = "msg-006",
            message = "Perfect! Let me search for morning flights from your location to Mumbai. Could you also share your departure city?",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = 1703520210000L
        ),
        Message(
            id = "msg-007",
            message = "",
            type = MessageType.FILE,
            file = FileAttachment(
                path = "https://images.unsplash.com/photo-1436491865332-7a61a109cc05?w=400",
                fileSize = 245680L,
                thumbnail = Thumbnail("https://images.unsplash.com/photo-1436491865332-7a61a109cc05?w=100")
            ),
            sender = Sender.USER,
            timestamp = 1703520300000L
        ),
        Message(
            id = "msg-008",
            message = "Thanks for sharing! I can see you prefer IndiGo. Let me find the best options for you.",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = 1703520330000L
        ),
        Message(
            id = "msg-009",
            message = "Flight options comparison",
            type = MessageType.FILE,
            file = FileAttachment(
                path = "https://images.unsplash.com/photo-1464037866556-6812c9d1c72e?w=400",
                fileSize = 189420L,
                thumbnail = Thumbnail("https://images.unsplash.com/photo-1464037866556-6812c9d1c72e?w=100")
            ),
            sender = Sender.AGENT,
            timestamp = 1703520420000L
        ),
        Message(
            id = "msg-010",
            message = "The second option looks perfect! How do I proceed?",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.USER,
            timestamp = 1703520480000L
        ),
        Message(
            id = "msg-011",
            message = "Excellent choice! I'll need some passenger details. Could you provide your full name as per your ID?",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = 1703520540000L
        ),
        Message(
            id = "msg-012",
            message = "Raj Kumar Sharma",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.USER,
            timestamp = 1703520600000L
        ),
        Message(
            id = "msg-013",
            message = "Great! And your contact number?",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = 1703520630000L
        ),
        Message(
            id = "msg-014",
            message = "+91 9876543210",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.USER,
            timestamp = 1703520660000L
        ),
        Message(
            id = "msg-015",
            message = "Perfect! And your email address for the booking confirmation?",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = 1703520690000L
        ),
        Message(
            id = "msg-016",
            message = "raj.sharma@email.com",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.USER,
            timestamp = 1703520720000L
        ),
        Message(
            id = "msg-017",
            message = "Excellent! Let me process your booking. This will take just a moment.",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = 1703520750000L
        ),
        Message(
            id = "msg-018",
            message = "",
            type = MessageType.FILE,
            file = FileAttachment(
                path = "https://images.unsplash.com/photo-1556388158-158ea5ccacbd?w=400",
                fileSize = 312450L,
                thumbnail = Thumbnail("https://images.unsplash.com/photo-1556388158-158ea5ccacbd?w=100")
            ),
            sender = Sender.AGENT,
            timestamp = 1703520810000L
        ),
        Message(
            id = "msg-019",
            message = "Your booking is confirmed! Here's your e-ticket. Reference number: IND2901234",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = 1703520840000L
        ),
        Message(
            id = "msg-020",
            message = "Awesome! Thank you so much for your help!",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.USER,
            timestamp = 1703520900000L
        ),
        Message(
            id = "msg-021",
            message = "You're welcome! Is there anything else I can help you with?",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = 1703520930000L
        ),
        Message(
            id = "msg-022",
            message = "Yes, can you suggest some hotels in Mumbai?",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.USER,
            timestamp = 1703520990000L
        ),
        Message(
            id = "msg-023",
            message = "Of course! I'd be happy to help with hotel recommendations. What's your budget per night?",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = 1703521020000L
        ),
        Message(
            id = "msg-024",
            message = "Around ₹5000-7000 per night. Preferably near the airport.",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.USER,
            timestamp = 1703521080000L
        ),
        Message(
            id = "msg-025",
            message = "Perfect! Let me find some great options for you in that price range near Mumbai Airport.",
            type = MessageType.TEXT,
            file = null,
            sender = Sender.AGENT,
            timestamp = 1703521110000L
        )
    )
}