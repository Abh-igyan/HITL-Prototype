from post_triples import post_batch

# fine-tuned SLM extraction results
triples = [
    {
        "text": "महात्मा गांधी का जन्म पोरबंदर में हुआ था।",
        "subject": "महात्मा_गांधी",
        "predicate": "dbo:birthPlace",
        "obj": "पोरबंदर",
        "source": "https://hi.wikipedia.org/wiki/Mahatma_Gandhi"
    },
    {
        "text": "नेपाल की राजधानी काठमांडू है।",
        "subject": "नेपाल",
        "predicate": "dbo:capital",
        "obj": "काठमांडू"
    }
]

results = post_batch(triples)
for r in results:
    print(f"Saved triple ID: {r['id']} | Status: {r['status']}")
