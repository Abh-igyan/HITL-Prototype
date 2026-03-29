import requests

BASE_URL = "http://localhost:8080/api/triples"

def post_triple(text, subject, predicate, obj, source=""):
    payload = {
        "originalText": text,
        "subject": subject,
        "predicate": predicate,
        "object": obj,
        "sourceUrl": source
    }
    r = requests.post(BASE_URL, json=payload)
    r.raise_for_status()
    return r.json()

def post_batch(triples):
    return [post_triple(**t) for t in triples]
