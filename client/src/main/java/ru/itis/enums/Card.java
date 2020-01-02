package ru.itis.enums;

import javafx.scene.paint.Color;

public class Card {
    private Value value;
    private Suit suit;

    public Card(Integer value, String suit) {
        this(
            Value.getByValue(value),
            Suit.valueOf(suit.toUpperCase())
        );
    }

    public Card(Value value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public Value getValue() {
        return value;
    }

    public Suit getSuit() {
        return suit;
    }

    public Color getColor() {
        return (suit.isRed()) ? Color.RED : Color.BLACK;
    }

    public String serialize() {
        return serialize(this);
    }

    public static String serialize(Card card) {
        String template = "%s_%s";
        return String.format(template, card.value, card.suit.name().toLowerCase());
    }

    public static Card deserialize(String json) {
        String[] obj = json.split("_");
        return new Card(Integer.parseInt(obj[0]), obj[1]);
    }

    @Override
    public String toString() {
        return String.format("%s%s", suit, value);
    }

    public String toDetails() {
        String signature = toString();
        switch (signature) {
            case "♣A":  return "\uD83C\uDCD1";
            case "♣2":  return "\uD83C\uDCD2";
            case "♣3":  return "\uD83C\uDCD3";
            case "♣4":  return "\uD83C\uDCD4";
            case "♣5":  return "\uD83C\uDCD5";
            case "♣6":  return "\uD83C\uDCD6";
            case "♣7":  return "\uD83C\uDCD7";
            case "♣8":  return "\uD83C\uDCD8";
            case "♣9":  return "\uD83C\uDCD9";
            case "♣10": return "\uD83C\uDCDA";
            case "♣J":  return "\uD83C\uDCDB";
            case "♣Q":  return "\uD83C\uDCDD";
            case "♣K":  return "\uD83C\uDCDE";

            case "♦A":  return "\uD83C\uDCC1";
            case "♦2":  return "\uD83C\uDCC2";
            case "♦3":  return "\uD83C\uDCC3";
            case "♦4":  return "\uD83C\uDCC4";
            case "♦5":  return "\uD83C\uDCC5";
            case "♦6":  return "\uD83C\uDCC6";
            case "♦7":  return "\uD83C\uDCC7";
            case "♦8":  return "\uD83C\uDCC8";
            case "♦9":  return "\uD83C\uDCC9";
            case "♦10": return "\uD83C\uDCCA";
            case "♦J":  return "\uD83C\uDCCB";
            case "♦Q":  return "\uD83C\uDCCD";
            case "♦K":  return "\uD83C\uDCCE";

            case "♥A":  return "\uD83C\uDCB1";
            case "♥2":  return "\uD83C\uDCB2";
            case "♥3":  return "\uD83C\uDCB3";
            case "♥4":  return "\uD83C\uDCB4";
            case "♥5":  return "\uD83C\uDCB5";
            case "♥6":  return "\uD83C\uDCB6";
            case "♥7":  return "\uD83C\uDCB7";
            case "♥8":  return "\uD83C\uDCB8";
            case "♥9":  return "\uD83C\uDCB9";
            case "♥10": return "\uD83C\uDCBA";
            case "♥J":  return "\uD83C\uDCBB";
            case "♥Q":  return "\uD83C\uDCBD";
            case "♥K":  return "\uD83C\uDCBE";

            case "♠A":  return "\uD83C\uDCA1";
            case "♠2":  return "\uD83C\uDCA2";
            case "♠3":  return "\uD83C\uDCA3";
            case "♠4":  return "\uD83C\uDCA4";
            case "♠5":  return "\uD83C\uDCA5";
            case "♠6":  return "\uD83C\uDCA6";
            case "♠7":  return "\uD83C\uDCA7";
            case "♠8":  return "\uD83C\uDCA8";
            case "♠9":  return "\uD83C\uDCA9";
            case "♠10": return "\uD83C\uDCAA";
            case "♠J":  return "\uD83C\uDCAB";
            case "♠Q":  return "\uD83C\uDCAD";
            case "♠K":  return "\uD83C\uDCAE";

            default:    return "\uD83C\uDCA0";
        }
    }
}

enum Value {
    ACE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    SEVEN(7),
    EIGHT(8),
    NINE(9),
    TEN(10),
    JACK(11),
    QUEEN(12),
    KING(13);

    private int value;

    Value(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        switch (this) {
            case ACE:
                return "A";
            case JACK:
                return "J";
            case QUEEN:
                return "Q";
            case KING:
                return "K";
            default:
                return String.valueOf(this.value);
        }
    }

    public static Value getByValue(Integer value) {
        for (Value v : values()) {
            if (v.value == value) {
                return v;
            }
        }
        return null;
    }
}

enum Suit {
    CLUBS(false, true),
    DIAMONDS(true, false),
    HEARTS(true, false),
    SPADES(false, true);

    private Boolean red;
    private Boolean black;

    public Boolean isRed() {
        return red;
    }

    public Boolean isBlack() {
        return black;
    }

    Suit(Boolean red, Boolean black) {
        this.red = red;
        this.black = black;
    }

    @Override
    public String toString() {
        switch (this) {
            case CLUBS:
                return "♣";
            case DIAMONDS:
                return "♦";
            case HEARTS:
                return "♥";
            case SPADES:
                return "♠";
            default:
                return "?";
        }
    }
}

class Main {
    public static void main(String[] args) {
        for (Value val: Value.values()) {
            for (Suit suit: Suit.values()) {
                Card card = new Card(val, suit);
                System.out.printf("%s: %s\n", card.toString(), card.toDetails());

            }
        }
    }
}
